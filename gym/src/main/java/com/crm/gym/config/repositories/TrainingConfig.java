package com.crm.gym.config.repositories;

import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;

import com.crm.gym.repositories.interfaces.TrainingRepository;
import com.crm.gym.entities.Training;

@Configuration
@DependsOn({"traineeConfig", "trainerConfig"})
public class TrainingConfig extends TemplateConfig<Long, Training>
{
    public TrainingConfig(@Value("${storage.trainings.path:}") String trainingsPath,
                          TrainingRepository trainingRepository,
                          EntityResourceLoader entityResourceLoader)
    {
        super(trainingsPath, trainingRepository, entityResourceLoader);
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}
}
