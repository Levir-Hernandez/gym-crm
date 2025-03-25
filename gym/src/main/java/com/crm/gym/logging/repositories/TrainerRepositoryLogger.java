package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TrainerRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainerRepositoryLogger extends TemplateRepositoryLogger<Long, Trainer>
{
    public TrainerRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TrainerRepository.class));
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TrainerRepository)")
    public void target_EntityRepository() {}
}
