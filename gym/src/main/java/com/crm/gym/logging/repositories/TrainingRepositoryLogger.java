package com.crm.gym.logging.repositories;

import com.crm.gym.entities.Training;
import com.crm.gym.repositories.interfaces.TrainingRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingRepositoryLogger extends TemplateRepositoryLogger<Long, Training>
{
    public TrainingRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TrainingRepository.class));
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TrainingRepository)")
    public void target_EntityRepository() {}
}
