package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Training;
import com.crm.gym.repositories.interfaces.TrainingRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Aspect
@Component
public class TrainingRepositoryLogger extends TemplateRepositoryLogger<Long, Training>
{
    private final String LIST = "java.util.List";
    private final String TRAINING_QUERY_CRITERIA = "com.crm.gym.repositories.TrainingQueryCriteria";

    public TrainingRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TrainingRepository.class));
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    // Pointcuts

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TrainingRepository)")
    public void target_EntityRepository() {}

    @Pointcut("execution("+LIST+" findTrainingsByCriteria("+TRAINING_QUERY_CRITERIA+"))")
    public void findTrainingsByCriteria() {}

    // Advices

    @Override
    public Training around_create(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Training training = (Training) args[0];

        // Assumes the following behavior for foreign key references:
        // - null values remain null
        // - valid non-null references remain unchanged
        // - invalid non-null references are automatically set to null

        boolean hadReference = Stream.of(training.getTrainingType(), training.getTrainee(),
                training.getTrainer()).anyMatch(Objects::nonNull);

        training = super.around_create(jp);

        boolean referenceWasCleared = Stream.of(training.getTrainingType(), training.getTrainee(),
                training.getTrainer()).anyMatch(Objects::isNull);

        if(hadReference && referenceWasCleared)
        {
            logger.warn("Non-existent foreign key references detected. Replaced with null");
        }

        return training;
    }

    @Before("target_EntityRepository() && findTrainingsByCriteria()")
    public void before_findTrainingsByCriteria()
    {
        logger.info("Fetching trainings based on provided criteria");
    }
}
