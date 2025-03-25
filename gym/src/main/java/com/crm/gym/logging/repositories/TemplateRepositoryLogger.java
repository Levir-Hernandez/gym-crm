package com.crm.gym.logging.repositories;

import com.crm.gym.repositories.interfaces.Identifiable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import java.util.Objects;

public abstract class TemplateRepositoryLogger<Id, Entity extends Identifiable<Id>>
{
    private final Logger logger;
    protected final String IDENTIFIABLE = "com.crm.gym.repositories.interfaces.Identifiable";

    public TemplateRepositoryLogger(Logger logger)
    {
        this.logger = logger;
    }

    protected abstract Class<Entity> getEntityClass();

    // Pointcuts

    public abstract void target_EntityRepository();

    @Pointcut("within(com.crm.gym.repositories.interfaces.TemplateRepository+)")
    public void within_TemplateRepositorySubclasses(){}

    @Pointcut("execution("+IDENTIFIABLE+" create("+IDENTIFIABLE+"))")
    public void create() {};

    @Pointcut("execution("+IDENTIFIABLE+" update(Object," + IDENTIFIABLE + "))")
    public void update() {};

    @Pointcut("execution("+IDENTIFIABLE+" delete(Object))")
    public void delete() {};

    @Pointcut("execution("+IDENTIFIABLE+" findById(Object))")
    public void findById() {};

    // Advices

    @Around("target_EntityRepository() && within_TemplateRepositorySubclasses() && create()")
    public Entity around_create(ProceedingJoinPoint jp) throws Throwable
    {
        logger.info("Storing new {} in-memory", getEntityClass().getSimpleName());
        Entity entity = (Entity) jp.proceed();
        logger.info("Stored new {} {} in-memory", getEntityClass().getSimpleName(), entity.getId());

        return entity;
    }

    @Around("target_EntityRepository() && within_TemplateRepositorySubclasses() && update()")
    public Entity around_update(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        logger.info("Saving changes for {} {} in-memory", getEntityClass().getSimpleName(), id);
        Entity entity = (Entity) jp.proceed(args);

        if(Objects.nonNull(entity))
        {
            logger.info("{} {} changed in-memory", getEntityClass().getSimpleName(), entity.getId());
        }
        else
        {
            logger.warn("{} {} does not exist. Update skipped", getEntityClass().getSimpleName(), id);
        }

        return entity;
    }

    @Around("target_EntityRepository() && within_TemplateRepositorySubclasses() && delete()")
    public Entity around_delete(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        Entity entity = (Entity) jp.proceed(args);
        if(Objects.nonNull(entity))
        {
            logger.info("{} {} deleted in-memory", getEntityClass().getSimpleName(), entity.getId());
        }
        else
        {
            logger.warn("{} {} does not exist. Delete skipped", getEntityClass().getSimpleName(), id);
        }
        return entity;
    }

    @Around("target_EntityRepository() && within_TemplateRepositorySubclasses() && findById()")
    public Entity around_findById(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        Object id = args[0];

        Entity entity = (Entity) jp.proceed(args);
        if(Objects.nonNull(entity))
        {
            logger.info("Fetching {} {} from in-memory storage", getEntityClass().getSimpleName(), entity.getId());
        }
        else
        {
            logger.warn("{} {} not found. Returning null", getEntityClass().getSimpleName(), id);
        }
        return entity;
    }
}