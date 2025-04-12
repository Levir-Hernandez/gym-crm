package com.crm.gym.logging.services;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TraineeService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class TraineeServiceLogger extends UserServiceLogger<Trainee>
{
    private final String SET = "java.util.Set";

    public TraineeServiceLogger()
    {
        super(LoggerFactory.getLogger(TraineeService.class));
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    // Pointcuts

    @Override
    @Pointcut("target(com.crm.gym.services.TraineeService)")
    public void target_EntityService() {}

    @Pointcut("execution(boolean deleteTraineeByUsername(String))")
    public void deleteTraineeByUsername() {}

    @Pointcut("execution(int updateAssignedTrainersForTrainee(String,"+SET+"))")
    public void updateAssignedTrainersForTrainee() {}

    // Advices

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && updateEntity()")
    public Trainee around_updateEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_updateEntity(jp);
    }

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && deleteEntity()")
    public boolean around_deleteEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_deleteEntity(jp);
    }

    @Around("target_EntityService() && deleteTraineeByUsername()")
    public boolean around_deleteTraineeByUsername(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_deleteEntity(jp);
    }

    @Around("target_EntityService() && updateAssignedTrainersForTrainee()")
    public int around_updateAssignedTrainersForTrainee(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        String username = (String) args[0];
        Set<Trainer> trainers = (Set<Trainer>) args[1];

        logger.info("Updating {} trainers(s) for Trainee {}", trainers.size(), username);
        int updatedTrainers = (int) jp.proceed();
        logger.info("Updated {}/{} trainer(s) for Trainee {}", updatedTrainers, trainers.size(), username);

        return updatedTrainers;
    }
}
