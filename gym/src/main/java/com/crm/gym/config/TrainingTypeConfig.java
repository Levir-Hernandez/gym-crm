package com.crm.gym.config;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TrainingTypeConfig
{
    private IdGenerator<Long> idGenerator;

    public TrainingTypeConfig(IdGenerator<Long> idGenerator)
    {
        this.idGenerator = idGenerator;
    }

    @Bean
    public TrainingType trainingType1()
    {
        return createTrainingType("Strength Training");
    }

    @Bean
    public TrainingType trainingType2()
    {
        return createTrainingType("Cardio");
    }

    @Bean
    public TrainingType trainingType3()
    {
        return createTrainingType("Flexibility & Mobility");
    }

    @Bean
    public Map<Long, TrainingType> trainingTypes(List<TrainingType> trainingTypes)
    {
        return trainingTypes.stream()
                .collect(Collectors.toMap(TrainingType::getId, trainingType -> trainingType));
    }

    private TrainingType createTrainingType(String name)
    {
        TrainingType trainingType = new TrainingType(null, name);
        trainingType.setId(idGenerator.generateId());
        return trainingType;
    }
}
