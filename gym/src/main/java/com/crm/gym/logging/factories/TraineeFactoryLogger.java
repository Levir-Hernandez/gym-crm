package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.factories.TraineeFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraineeFactoryLogger
{
    private final Logger logger = LoggerFactory.getLogger(TraineeFactory.class);

    private final String TRAINEE = "com.crm.gym.entities.Trainee";

    @Pointcut("within(com.crm.gym.factories.TraineeFactory) && execution("+TRAINEE+" recreate("+TRAINEE+"))")
    public void recreate(){}

    @AfterReturning(pointcut = "recreate()", returning = "trainee")
    public void afterReturning_recreate(Trainee trainee)
    {
        String username = trainee.getUsername();
        String password = trainee.getPassword();

        //username = "*".repeat(username.length());
        //password = "*".repeat(password.length());

        logger.info("Generated Trainee (id:{}, username:{}, password:{})",
                trainee.getId(), username, password);
    }
}
