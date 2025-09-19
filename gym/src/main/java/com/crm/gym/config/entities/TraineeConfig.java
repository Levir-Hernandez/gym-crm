package com.crm.gym.config.entities;

import com.crm.gym.services.TraineeService;
import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.entities.Trainee;

import java.util.UUID;

@Configuration
public class TraineeConfig extends TemplateConfig<UUID, Trainee, TraineeRepository>
{
    public TraineeConfig(@Value("${storage.trainees.path:}") String traineesPath,
                         TraineeService traineeService,
                         EntityResourceLoader entityResourceLoader)
    {
        super(traineesPath, traineeService, entityResourceLoader);
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}
}
