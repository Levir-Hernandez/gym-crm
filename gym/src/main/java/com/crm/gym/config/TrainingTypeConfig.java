package com.crm.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.gym.entities.TrainingType;

@Configuration
public class TrainingTypeConfig extends TemplateConfig<Long, TrainingType>
{
    public TrainingTypeConfig(ObjectMapper mapper,
                              @Value("${storage.training-types.path}") String trainingTypesPath,
                              TrainingTypeRepository trainingTypeRepository)
    {
        super(mapper, trainingTypesPath, trainingTypeRepository);
    }

    @Override
    protected Class<TrainingType> getEntityClass() {return TrainingType.class;}

    @PostConstruct
    public void createTrainingTypesFromJson() {createEntitiesFromJson("trainingType");}
}
