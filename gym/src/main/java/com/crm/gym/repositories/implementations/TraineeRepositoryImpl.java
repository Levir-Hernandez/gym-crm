package com.crm.gym.repositories.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.*;
import jakarta.persistence.Tuple;
import java.util.stream.Collectors;
import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.Trainee;
import jakarta.persistence.TypedQuery;
import com.crm.gym.factories.TraineeFactory;
import com.crm.gym.repositories.interfaces.MassiveUpdateRepository;

@Repository
public class TraineeRepositoryImpl
        extends TemplateRepositoryImpl<Long, Trainee>
        implements MassiveUpdateRepository
{
    private TraineeFactory traineeFactory;

    public TraineeRepositoryImpl(TraineeFactory traineeFactory)
    {
        this.traineeFactory = traineeFactory;
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @Override
    public Trainee create(Trainee trainee)
    {
        trainee = traineeFactory.recreate(trainee);
        return super.create(trainee);
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
