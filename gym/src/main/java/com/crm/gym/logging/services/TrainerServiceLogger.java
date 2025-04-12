package com.crm.gym.logging.services;

import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
    public class TrainerServiceLogger extends UserServiceLogger<Trainer>
{
    private final String LIST = "java.util.List";

    public TrainerServiceLogger()
    {
        super(LoggerFactory.getLogger(TrainerService.class));
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    @Pointcut("target(com.crm.gym.services.TrainerService)")
    public void target_EntityService() {}

    @Pointcut("execution("+LIST+" getAllUnassignedForTraineeByUsername(String))")
    public void getAllUnassignedForTraineeByUsername() {}

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && updateEntity()")
    public Trainer around_updateEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_updateEntity(jp);
    }

    @Around("target_EntityService() && getAllUnassignedForTraineeByUsername()")
    public List<Trainer> around_getAllUnassignedForTraineeByUsername(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        String username = (String) args[0];

        logger.info("Searching for trainers not assigned to Trainee {}", username);

        List<Trainer> unassignedTrainers = (List<Trainer>) jp.proceed();

        logger.info("Found {} unassigned trainer(s) for Trainee {}", unassignedTrainers.size(), username);

        return unassignedTrainers;
    }
}
