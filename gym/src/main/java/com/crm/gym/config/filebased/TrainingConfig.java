package com.crm.gym.config.filebased;

import com.crm.gym.entities.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TrainingConfig extends TemplateConfig<Long, Training>
{
    public TrainingConfig(ObjectMapper mapper,
                         @Value("${storage.trainings.path}") String trainingsPath)
    {
        super(mapper, trainingsPath);
    }

    @Override
    protected Class<Training> getEntityClass() {return Training.class;}

    @PostConstruct
    public void createTrainings() {createEntities("training");}

    @Bean
    public Map<Long, Training> trainings(List<Training> trainings) {return entities(trainings);}
}
