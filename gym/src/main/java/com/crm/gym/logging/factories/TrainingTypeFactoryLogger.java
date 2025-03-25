package com.crm.gym.logging.factories;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.factories.TrainingTypeFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingTypeFactoryLogger
{
    private final Logger logger = LoggerFactory.getLogger(TrainingTypeFactory.class);

    private final String TRAINING_TYPE = "com.crm.gym.entities.TrainingType";

    @Pointcut("within(com.crm.gym.factories.TrainingTypeFactory) && execution("+ TRAINING_TYPE +" recreate("+ TRAINING_TYPE +"))")
    public void recreate(){}

    @AfterReturning(pointcut = "recreate()", returning = "trainingType")
    public void afterReturning_recreate(TrainingType trainingType)
    {
        logger.info("Generated TrainingType (id:{})", trainingType.getId());
    }
}
