package com.crm.gym.config.entities;

import com.crm.gym.services.TrainingService;
import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;

import com.crm.gym.repositories.interfaces.TrainingRepository;
import com.crm.gym.entities.Training;

@Configuration
@DependsOn({"traineeConfig", "trainerConfig"})
public class TrainingConfig extends TemplateConfig<Long, Training, TrainingRepository>
{
    public TrainingConfig(@Value("${storage.trainings.path:}") String trainingsPath,
                          TrainingService trainingService,
                          EntityResourceLoader entityResourceLoader)
    {
        super(trainingsPath, trainingService, entityResourceLoader);
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}
}
