package com.crm.gym.logging.services;

import com.crm.gym.repositories.interfaces.Identifiable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import java.util.Objects;

public abstract class TemplateServiceLogger<Id, Entity extends Identifiable<Id>>
{
    private final Logger logger;
    private final String IDENTIFIABLE = "com.crm.gym.repositories.interfaces.Identifiable";

    public TemplateServiceLogger(Logger logger)
    {
        this.logger = logger;
    }

    protected abstract Class<Entity> getEntityClass();

    // Pointcuts

    public abstract void target_EntityService();

    @Pointcut("within(com.crm.gym.services.TemplateService+)")
    public void within_TemplateServiceSubclasses() {}

    @Pointcut("execution("+IDENTIFIABLE+" saveEntity("+IDENTIFIABLE+"))")
    public void saveEntity() {}

    @Pointcut("execution("+IDENTIFIABLE+" updateEntity(Object," + IDENTIFIABLE + "))")
    public void updateEntity() {}

    @Pointcut("execution("+IDENTIFIABLE+" deleteEntity(Object))")
    public void deleteEntity() {}

    @Pointcut("execution("+IDENTIFIABLE+" getEntityById(Object))")
    public void getEntityById() {}

    // Advices

    @Around("target_EntityService() && within_TemplateServiceSubclasses() && saveEntity()")
    public Entity around_saveEntity(ProceedingJoinPoint jp) throws Throwable
    {
        logger.info("Creating new {}", getEntityClass().getSimpleName());
        Entity entity = (Entity) jp.proceed();
        logger.info("Created new {} {}", getEntityClass().getSimpleName(), entity.getId());

        return entity;
    }

    protected Entity around_updateEntity(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        logger.info("Updating {} {}", getEntityClass().getSimpleName(), id);
        Entity entity = (Entity) jp.proceed(args);

        if(Objects.nonNull(entity))
        {
            logger.info("{} {} updated", getEntityClass().getSimpleName(), entity.getId());
        }

        return entity;
    }

    protected Entity around_deleteEntity(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        logger.info("Deleting {} {}", getEntityClass().getSimpleName(), id);
        Entity entity = (Entity) jp.proceed(args);

        if(Objects.nonNull(entity))
        {
            logger.info("{} {} deleted", getEntityClass().getSimpleName(), entity.getId());
        }

        return entity;
    }

    @Around("target_EntityService() && within_TemplateServiceSubclasses() && getEntityById()")
    protected Entity around_getEntityById(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        logger.info("Searching {} {}", getEntityClass().getSimpleName(), id);
        Entity entity = (Entity) jp.proceed(args);

        if(Objects.nonNull(entity))
        {
            logger.info("{} {} retrieved", getEntityClass().getSimpleName(), entity.getId());
        }

        return entity;
    }
}
