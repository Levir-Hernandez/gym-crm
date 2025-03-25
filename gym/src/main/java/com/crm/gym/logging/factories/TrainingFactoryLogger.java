package com.crm.gym.logging.factories;

import com.crm.gym.entities.Training;
import com.crm.gym.factories.TrainingFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingFactoryLogger
{
    private final Logger logger = LoggerFactory.getLogger(TrainingFactory.class);

    private final String TRAINING = "com.crm.gym.entities.Training";

    @Pointcut("within(com.crm.gym.factories.TrainingFactory) && execution("+ TRAINING +" recreate("+ TRAINING +"))")
    public void recreate(){}

    @AfterReturning(pointcut = "recreate()", returning = "training")
    public void afterReturning_recreate(Training training)
    {
        logger.info("Generated Training (id:{})", training.getId());
    }
}
