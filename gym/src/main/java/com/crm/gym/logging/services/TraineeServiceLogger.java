package com.crm.gym.logging.services;

import com.crm.gym.entities.Trainee;
import com.crm.gym.services.TraineeService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraineeServiceLogger extends TemplateServiceLogger<Long, Trainee>
{
    public TraineeServiceLogger()
    {
        super(LoggerFactory.getLogger(TraineeService.class));
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @Override
    @Pointcut("target(com.crm.gym.services.TraineeService)")
    public void target_EntityService() {}

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && updateEntity()")
    public Trainee around_updateEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_updateEntity(jp);
    }

    @Override
    @Around("target_EntityService() && within_TemplateServiceSubclasses() && deleteEntity()")
    public Trainee around_deleteEntity(ProceedingJoinPoint jp) throws Throwable
    {
        return super.around_deleteEntity(jp);
    }
}
