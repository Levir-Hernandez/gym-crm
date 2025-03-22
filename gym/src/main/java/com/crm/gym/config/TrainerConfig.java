package com.crm.gym.config;

import com.crm.gym.entities.Trainer;
import com.crm.gym.factories.TrainerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TrainerConfig extends TemplateConfig<Long, Trainer>
{
    public TrainerConfig(ObjectMapper mapper,
                         @Value("${storage.trainers.path}") String trainersPath,
                         TrainerFactory trainerFactory)
    {
        super(mapper, trainersPath, trainerFactory);
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @PostConstruct
    public void createTrainers() {createEntities("trainer");}

    @Bean
    public Map<Long, Trainer> trainers(List<Trainer> trainers) {return entities(trainers);}
}
