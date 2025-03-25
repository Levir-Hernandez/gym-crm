package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.TraineeRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraineeRepositoryLogger extends TemplateRepositoryLogger<Long, Trainee>
{
    public TraineeRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TraineeRepository.class));
    }
    
    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TraineeRepository)")
    public void target_EntityRepository() {}
}
