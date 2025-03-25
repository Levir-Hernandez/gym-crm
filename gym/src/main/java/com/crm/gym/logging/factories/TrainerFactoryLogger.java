package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.factories.TraineeFactory;
import com.crm.gym.factories.TrainerFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainerFactoryLogger
{
    private final Logger logger = LoggerFactory.getLogger(TrainerFactory.class);

    private final String TRAINER = "com.crm.gym.entities.Trainer";

    @Pointcut("within(com.crm.gym.factories.TrainerFactory) && execution("+ TRAINER +" recreate("+ TRAINER +"))")
    public void recreate(){}

    @AfterReturning(pointcut = "recreate()", returning = "trainer")
    public void afterReturning_recreate(Trainer trainer)
    {
        String username = trainer.getUsername();
        String password = trainer.getPassword();

        //username = "*".repeat(username.length());
        //password = "*".repeat(password.length());

        logger.info("Generated Trainer (id:{}, username:{}, password:{})",
                trainer.getId(), username, password);
    }
}
