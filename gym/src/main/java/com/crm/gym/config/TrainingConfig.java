package com.crm.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

import com.crm.gym.repositories.interfaces.TrainingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.gym.entities.Training;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn({"traineeConfig", "trainerConfig"})
public class TrainingConfig extends TemplateConfig<Long, Training>
{
    public TrainingConfig(ObjectMapper mapper,
                          @Value("${storage.trainings.path}") String trainingsPath,
                          TrainingRepository trainingRepository)
    {
        super(mapper, trainingsPath, trainingRepository);
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    @PostConstruct
    public void createTrainingsFromJson() {createEntitiesFromJson("training");}
}
