package com.crm.gym.logging.services;

import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainerServiceLogger extends TemplateServiceLogger<Long, Trainer>
{
    public TrainerServiceLogger()
    {
        super(LoggerFactory.getLogger(TrainerService.class));
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    @Pointcut("target(com.crm.gym.services.TrainerService)")
    public void target_EntityService() {}

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && updateEntity()")
    public Trainer around_updateEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_updateEntity(jp);
    }
}
