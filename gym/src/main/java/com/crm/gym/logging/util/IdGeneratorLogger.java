package com.crm.gym.logging.util;

import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;

import com.crm.gym.util.IdGenerator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Aspect
@Component
public class IdGeneratorLogger
{
    private final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    @Pointcut("within(com.crm.gym.util.IdGenerator+) && execution(Object generateId())")
    public void generateId() {}

    @AfterReturning(pointcut = "generateId()", returning = "id")
    public void afterReturning_generateId(Object id)
    {
        logger.debug("Generated new id: {}", id);
    }
}
