package com.crm.gym.logging.repositories;

import com.crm.gym.entities.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class UserRepositoryLogger<S extends User> extends TemplateRepositoryLogger<Long, S>
{
    private final String USER = "com.crm.gym.entities.User";
    protected final String OPTIONAL = "java.util.Optional";

    public UserRepositoryLogger(Logger logger)
    {
        super(logger);
    }

    // Pointcuts

    @Pointcut("execution("+OPTIONAL+" findByUsername(String))")
    public void findByUsername() {}

    @Pointcut("execution("+USER+" updateByUsername(String,"+USER+"))")
    public void updateByUsername() {}

    @Pointcut("execution(boolean existsByUsernameAndPassword(String,String))")
    public void existsByUsernameAndPassword() {}

    @Around("target_EntityRepository() && findByUsername()")
    public Optional<S> around_findByUsername(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_findById(jp);
    }

    @Around("target_EntityRepository() && updateByUsername()")
    public S around_updateByUsername(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_update(jp);
    }

    @Around("target_EntityRepository() && existsByUsernameAndPassword()")
    public boolean around_existsByUsernameAndPassword(ProceedingJoinPoint jp) throws Throwable
    {
        Object[] args = jp.getArgs();
        String username = (String) args[0];

        logger.info("Verifying User exists with the given username and password");

        boolean exists = (boolean) jp.proceed();
        if(exists)
        {
            logger.info("User {} found in the database", username);
        }
        else
        {
            logger.warn("User {} not found with the provided password", username);
        }

        return exists;
    }
}
