package com.crm.gym.logging.factories;

import com.crm.gym.entities.Trainer;
import com.crm.gym.factories.TrainerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainerFactoryLogger extends TemplateFactoryLogger<Long, Trainer>
{
    public TrainerFactoryLogger()
    {
        super(LoggerFactory.getLogger(TrainerFactory.class));
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    @Pointcut("target(com.crm.gym.factories.TrainerFactory)")
    public void target_EntityFactory() {}
}
