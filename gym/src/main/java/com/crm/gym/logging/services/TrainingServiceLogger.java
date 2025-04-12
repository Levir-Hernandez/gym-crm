package com.crm.gym.logging.services;

import com.crm.gym.entities.Training;
import com.crm.gym.services.TrainingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class TrainingServiceLogger extends TemplateServiceLogger<Long, Training>
{
    private final String LIST = "java.util.List";
    private final String TRAINING_QUERY_CRITERIA = "com.crm.gym.repositories.TrainingQueryCriteria";

    public TrainingServiceLogger()
    {
        super(LoggerFactory.getLogger(TrainingService.class));
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    // Pointcuts

    @Override
    @Pointcut("target(com.crm.gym.services.TrainingService)")
    public void target_EntityService() {}

    @Pointcut("execution("+LIST+" getTrainingsByTraineeUsernameAndCriteria("+TRAINING_QUERY_CRITERIA+"))")
    public void getTrainingsByTraineeUsernameAndCriteria() {}

    @Pointcut("execution("+LIST+" getTrainingsByTrainerUsernameAndCriteria("+TRAINING_QUERY_CRITERIA+"))")
    public void getTrainingsByTrainerUsernameAndCriteria() {}

    // Advices

    @Around("target_EntityService() && getTrainingsByTraineeUsernameAndCriteria()")
    public List<Training> around_getTrainingsByTraineeUsernameAndCriteria(ProceedingJoinPoint jp) throws Throwable
    {
        return around_getTrainingsByCriteria(jp);
    }

    @Around("target_EntityService() && getByTrainerUsernameAndCriteria()")
    public List<Training> around_getTrainingsByTrainerUsernameAndCriteria(ProceedingJoinPoint jp) throws Throwable
    {
        return around_getTrainingsByCriteria(jp);
    }

    private List<Training> around_getTrainingsByCriteria(ProceedingJoinPoint jp) throws Throwable
    {
        logger.info("Searching for trainings that match the given criteria");

        List<Training> trainings = (List<Training>) jp.proceed();
        if(Objects.nonNull(trainings))
        {
            logger.info("Found {} training(s) matching the criteria", trainings.size());
        }

        return trainings;
    }
}
