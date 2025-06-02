package com.crm.gym.config.repositories;

import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.entities.Trainee;

@Configuration
public class TraineeConfig extends TemplateConfig<Long, Trainee>
{
    public TraineeConfig(@Value("${storage.trainees.path:}") String traineesPath,
                         TraineeRepository traineeRepository,
                         EntityResourceLoader entityResourceLoader)
    {
        super(traineesPath, traineeRepository, entityResourceLoader);
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}
}
