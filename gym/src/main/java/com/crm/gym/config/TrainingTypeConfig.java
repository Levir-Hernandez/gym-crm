package com.crm.gym.config;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.factories.TrainingTypeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TrainingTypeConfig extends TemplateConfig<Long, TrainingType>
{
    public TrainingTypeConfig(ObjectMapper mapper,
                              @Value("${storage.training-types.path}") String trainingTypesPath,
                              TrainingTypeFactory trainingTypeFactory)
    {
        super(mapper, trainingTypesPath, trainingTypeFactory);
    }

    @Override
    protected Class<TrainingType> getEntityClass() {return TrainingType.class;}

    @PostConstruct
    public void createTrainings() {createEntities("trainingType");}

    @Bean
    public Map<Long, TrainingType> trainingTypes(List<TrainingType> trainingTypes) {return entities(trainingTypes);}
}
