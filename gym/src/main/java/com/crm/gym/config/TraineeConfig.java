package com.crm.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.gym.entities.Trainee;

@Configuration
public class TraineeConfig extends TemplateConfig<Long, Trainee>
{
    public TraineeConfig(ObjectMapper mapper,
                         @Value("${storage.trainees.path}") String traineesPath,
                         TraineeRepository traineeRepository)
    {
        super(mapper, traineesPath, traineeRepository);
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @PostConstruct
    public void createTraineesFromJson() {createEntitiesFromJson("trainee");}
}
