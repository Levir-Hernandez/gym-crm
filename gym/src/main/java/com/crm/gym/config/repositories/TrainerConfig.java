package com.crm.gym.config.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

import com.crm.gym.repositories.interfaces.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.gym.entities.Trainer;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("trainingTypeConfig")
public class TrainerConfig extends TemplateConfig<Long, Trainer>
{
    public TrainerConfig(ObjectMapper mapper,
                         @Value("${storage.trainers.path}") String trainersPath,
                         TrainerRepository trainerRepository)
    {
        super(mapper, trainersPath, trainerRepository);
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @PostConstruct
    public void createTrainersFromJson() {createEntitiesFromJson("trainer");}
}
