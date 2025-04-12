package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.Identifiable;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;

public abstract class TemplateFactoryLogger<Id, Entity extends Identifiable<Id>>
{
    private final Logger logger;
    protected final String IDENTIFIABLE = "com.crm.gym.repositories.interfaces.Identifiable";

    public TemplateFactoryLogger(Logger logger) {this.logger = logger;}

    protected abstract Class<Entity> getEntityClass();

    // Pointcuts

    public abstract void target_EntityFactory();

    @Pointcut("within(com.crm.gym.factories.TemplateFactory+)")
    public void within_TemplateFactorySubclasses(){}

    @Pointcut("execution("+IDENTIFIABLE+" recreate("+IDENTIFIABLE+"))")
    public void recreate(){}

    // Advices

    @AfterReturning(
            pointcut = "target_EntityFactory() && within_TemplateFactorySubclasses() && recreate()",
            returning = "trainee")
    public void afterReturning_recreate(Trainee trainee)
    {
        String username = trainee.getUsername();
        String password = trainee.getPassword();

        //username = "*".repeat(username.length());
        //password = "*".repeat(password.length());

        logger.info("Generated {} (username:{}, password:{})",
                getEntityClass().getSimpleName(), username, password);
    }
}
