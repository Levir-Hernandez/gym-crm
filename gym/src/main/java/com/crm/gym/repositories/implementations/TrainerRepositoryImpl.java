package com.crm.gym.repositories.implementations;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.factories.TrainerFactory;
import com.crm.gym.repositories.interfaces.MassiveUpdateRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.crm.gym.repositories.interfaces.TrainerRefsManager;

@Repository
public class TrainerRepositoryImpl extends TemplateRepositoryImpl<Long, Trainer>
        implements TrainerRefsManager, MassiveUpdateRepository
{
    private TrainerFactory trainerFactory;

    public TrainerRepositoryImpl(TrainerFactory trainerFactory)
    {
        this.trainerFactory = trainerFactory;
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    public Trainer create(Trainer trainer)
    {
        resolveReferencesByAltKeys(trainer);
        nullifyInvalidReferences(trainer);
        trainer = trainerFactory.recreate(trainer);
        return super.create(trainer);
    }

    @Override
    public Trainer update(Long entityId, Trainer trainer)
    {
        resolveReferencesByAltKeys(trainer);
        nullifyInvalidReferences(trainer);
        return super.update(entityId, trainer);
    }

    @Override
    public void nullifyInvalidReferences(Trainer trainer)
    {
        boolean invalidSpecialization = Optional
                .ofNullable(trainer.getSpecialization())
                .map(TrainingType::getId)
                .map(id -> em.find(TrainingType.class, id))
                .isEmpty();

        if(invalidSpecialization) {trainer.setSpecialization(null);}
    }

    @Override
    public void resolveReferencesByAltKeys(Trainer trainer)
    {
        TrainingType specialization = trainer.getSpecialization();
        if(Objects.isNull(specialization) || Objects.nonNull(specialization.getId()) || Objects.isNull(specialization.getName())) {return;}
        try
        {
            specialization = em.createQuery(
                            "SELECT t FROM TrainingType t WHERE t.name = :name",
                            TrainingType.class)
                    .setParameter("name", specialization.getName())
                    .getSingleResult();
        }
        catch (NoResultException e) {specialization = null;}
        trainer.setSpecialization(specialization);
    }

    @Override
    @Transactional
    public int updateAssignedTrainersForTrainee(String traineeUsername, Set<Trainer> trainers)
    {
        Set<String> usernames = trainers.stream()
                .map(Trainer::getUsername).collect(Collectors.toSet());

        Map<String, Long> idMap = fetchTrainersIds(traineeUsername, usernames);

        // Ensure trainers have appropriate ids before merging
        trainers.forEach(trainer -> {
            Long id = idMap.get(trainer.getUsername());
            trainer.setId(id);
        });

        // Exclude trainers with unknown usernames
        trainers = trainers.stream()
                .filter(trainer -> Objects.nonNull(trainer.getId()))
                .collect(Collectors.toSet());

        // Resolve trainer references by alternative keys
        trainers.forEach(this::resolveReferencesByAltKeys);

        // Merge valid trainers
        trainers.forEach(em::merge);

        return trainers.size();
    }

    private Map<String, Long> fetchTrainersIds(String traineeUsername, Set<String> usernames)
    {
        TypedQuery<Tuple> query = em.createQuery(
                "SELECT DISTINCT t3.username AS username, t3.id AS id " + // Fetch trainers' usernames and ids
                        "FROM Training t1 " +
                        "INNER JOIN t1.trainee t2 " +
                        "INNER JOIN t1.trainer t3 " +
                        "WHERE t2.username = :traineeUsername " + // associated with the specified trainee
                        "AND t3.username IN :usernames", // and included in the username list
                Tuple.class);

        query.setParameter("traineeUsername", traineeUsername);
        query.setParameter("usernames", usernames);

        return query.getResultList().stream()
                .collect(Collectors.toMap(
                                tuple -> tuple.get("username", String.class),
                                tuple -> tuple.get("id", Long.class)
                        )
                );
    }
}
