package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TraineeRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class TraineeRepositoryLogger extends UserRepositoryLogger<Trainee>
{
    private final String SET = "java.util.Set";

    public TraineeRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TraineeRepository.class));
    }
    
    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    // Pointcuts

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TraineeRepository)")
    public void target_EntityRepository() {}

    @Pointcut("execution(boolean deleteByUsernameIfExists(String))")
    public void deleteByUsernameIfExists() {}

    @Pointcut("execution(int updateAssignedTrainersForTrainee(String,"+SET+"))")
    public void updateAssignedTrainersForTrainee() {}

    // Advices

    @Around("target_EntityRepository() && within_TemplateRepositorySubclasses() && deleteByUsernameIfExists()")
    public boolean around_deleteByUsernameIfExists(ProceedingJoinPoint jp) throws Throwable
    {
        return around_delete(jp);
    }

    @AfterReturning(
            pointcut = "target_EntityRepository() && updateAssignedTrainersForTrainee()",
            returning = "updatedTrainers")
    public void afterReturning_recreate(JoinPoint jp, int updatedTrainers)
    {
        Object[] args = jp.getArgs();
        String username = (String) args[0];
        Set<Trainer> trainers = (Set<Trainer>) args[1];

        logger.info("Changed {} trainers(s) in database for Trainee {}", updatedTrainers, username);

        if(updatedTrainers < trainers.size())
        {
            logger.warn("Trainer(s) not assigned to the Trainee were skipped");
        }
    }
}
