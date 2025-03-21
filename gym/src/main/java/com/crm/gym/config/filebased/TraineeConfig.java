package com.crm.gym.config.filebased;

import com.crm.gym.entities.Trainee;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TraineeConfig extends TemplateConfig<Long, Trainee>
{
    public TraineeConfig(ObjectMapper mapper,
                         @Value("${storage.trainees.path}") String traineePath)
    {
        super(mapper, traineePath);
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @PostConstruct
    public void createTrainees() {createEntities("trainee");}

    @Bean
    public Map<Long, Trainee> trainees(List<Trainee> trainees) {return entities(trainees);}
}
