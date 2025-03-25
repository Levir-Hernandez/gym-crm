package com.crm.gym.logging.services;

import com.crm.gym.entities.Training;
import com.crm.gym.services.TrainingService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingServiceLogger extends TemplateServiceLogger<Long, Training>
{
    public TrainingServiceLogger()
    {
        super(LoggerFactory.getLogger(TrainingService.class));
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    @Override
    @Pointcut("target(com.crm.gym.services.TrainingService)")
    public void target_EntityService() {}
}
