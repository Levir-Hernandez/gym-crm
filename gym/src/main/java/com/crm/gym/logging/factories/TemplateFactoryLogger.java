package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.Identifiable;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class TemplateFactoryLogger<Id, Entity extends Identifiable<Id>>
{
    private final Logger logger;
    protected final String IDENTIFIABLE = "com.crm.gym.repositories.interfaces.Identifiable";

    private boolean showUsername;
    private boolean showPassword;

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

        if(!showUsername){username = "*".repeat(username.length());}
        if(!showPassword){password = "*".repeat(password.length());}

        logger.info("Generated {} (username:{}, password:{})",
                getEntityClass().getSimpleName(), username, password);
    }

    @Autowired
    private void setShowUsername(@Value("${logging.user-credentials.show-username:false}") boolean showUsername)
    {
        this.showUsername = showUsername;
    }

    @Autowired
    private void setShowPassword(@Value("${logging.user-credentials.show-password:false}") boolean showPassword)
    {
        this.showPassword = showPassword;
    }
}
