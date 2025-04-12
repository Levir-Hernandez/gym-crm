package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.Training;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.TrainingQueryCriteria;
import com.crm.gym.repositories.interfaces.DynamicQueryRepository;
import com.crm.gym.repositories.interfaces.Identifiable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Repository
public class TrainingRepositoryImpl
        extends TemplateRepositoryImpl<Long, Training>
        implements DynamicQueryRepository
{
    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    @Override
    public Training create(Training training)
    {
        nullifyInvalidReferences(training);
        return super.create(training);
    }

    @Override
    public Training update(Long entityId, Training training)
    {
        nullifyInvalidReferences(training);
        return super.update(entityId, training);
    }

    @Override
    public List<Training> findTrainingsByCriteria(TrainingQueryCriteria criteria)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> training = cq.from(Training.class);

        List<Predicate> predicates = buildTrainingPredicates(cb, training, criteria);

        cq.select(training).where(cb.and(predicates.toArray(Predicate[]::new)));

        return em.createQuery(cq).getResultList();
    }

    private void nullifyInvalidReferences(Training training)
    {
        BiPredicate<Identifiable<?>, Class<? extends Identifiable<?>>> isInvalidReference;

        isInvalidReference = (entity, entityClass) -> Optional
                .ofNullable(entity)
                .map(Identifiable::getId)
                .map(id -> em.find(entityClass, id))
                .isEmpty();

        boolean invalidTrainingType = isInvalidReference.test(training.getTrainingType(), TrainingType.class);
        boolean invalidTrainee = isInvalidReference.test(training.getTrainee(), Trainee.class);
        boolean invalidTrainer = isInvalidReference.test(training.getTrainer(), Trainer.class);

        if(invalidTrainingType) {training.setTrainingType(null);}
        if(invalidTrainee) {training.setTrainee(null);}
        if(invalidTrainer) {training.setTrainer(null);}
    }

    private List<Predicate> buildTrainingPredicates(CriteriaBuilder cb, Root<Training> training, TrainingQueryCriteria criteria)
    {
        List<Predicate> predicates = new ArrayList<>();

        BiConsumer<Object, Function<Object, Predicate>> addPredicate =
                (field, predicateMapper) ->
                        Optional.ofNullable(field)
                                .ifPresent(f -> predicates.add(predicateMapper.apply(f)));

        addPredicate.accept(
                criteria.getTraineeUsername(),
                traineeUsername -> cb.equal(training.get("trainee").get("username"), traineeUsername)
        );

        addPredicate.accept(
                criteria.getTrainerUsername(),
                trainerUsername -> cb.equal(training.get("trainer").get("username"), trainerUsername)
        );

        addPredicate.accept(
                criteria.getFromDate(),
                fromDate -> cb.greaterThanOrEqualTo(training.get("date"), (LocalDate) fromDate)
        );

        addPredicate.accept(
                criteria.getToDate(),
                toDate -> cb.lessThanOrEqualTo(training.get("date"), (LocalDate) toDate)
        );

        addPredicate.accept(
                criteria.getTrainingTypeName(),
                trainingTypeName -> cb.equal(training.get("trainingType").get("name"), trainingTypeName)
        );

        return predicates;
    }
}
