package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainee;
import com.crm.gym.factories.TraineeFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraineeFactoryLogger extends UserFactoryLogger<Trainee>
{
    public TraineeFactoryLogger()
    {
        super(LoggerFactory.getLogger(TraineeFactory.class));
    }

    @Override
    protected Class<Trainee> getUserClass() {return Trainee.class;}

    @Override
    @Pointcut("target(com.crm.gym.factories.TraineeFactory)")
    public void target_UserFactory() {}
}
