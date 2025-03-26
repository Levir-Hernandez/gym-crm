package com.crm.gym.logging.repositories;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingTypeRepositoryLogger extends TemplateRepositoryLogger<Long, TrainingType>
{
    public TrainingTypeRepositoryLogger()
    {
        super(LoggerFactory.getLogger(TrainingTypeRepository.class));
    }

    @Override
    protected Class<TrainingType> getEntityClass() {return TrainingType.class;}

    @Override
    @Pointcut("target(com.crm.gym.repositories.interfaces.TrainingTypeRepository)")
    public void target_EntityRepository() {}
}
