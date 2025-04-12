package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TrainerRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class TrainerRepositoryLogger extends TemplateRepositoryLogger<Long, Trainer>
{
    private final String LIST = "java.util.List";

    public TrainerRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TrainerRepository.class));
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    // Pointcuts

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TrainerRepository)")
    public void target_EntityRepository() {}

    @Pointcut("execution("+LIST+" findAllUnassignedForTraineeByUsername(String))")
    public void findAllUnassignedForTraineeByUsername() {}

    // Advices

    @Override
    public Trainer around_create(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Trainer trainer = (Trainer) args[0];

        boolean hadSpecialization = Objects.nonNull(trainer.getSpecialization());

        trainer = super.around_create(jp);

        boolean specializationWasCleared = Objects.isNull(trainer.getSpecialization());

        if(hadSpecialization && specializationWasCleared)
        {
            logger.warn("Non-existent foreign key references detected. Replaced with null");
        }

        return trainer;
    }

    @Before("target_EntityRepository() && findAllUnassignedForTraineeByUsername()")
    public void before_findAllUnassignedForTraineeByUsername(JoinPoint jp)
    {
        Object[] args = jp.getArgs();
        String username = (String) args[0];

        logger.info("Fetching trainers not assigned to Trainee {}", username);
    }
}
