package com.crm.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.repositories.interfaces.TrainerRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.gym.util.*;
import java.util.Random;

@Configuration
public class UtilConfig
{
    @Bean
    public UsernameGenerator usernameGenerator(@Lazy TraineeRepository traineeRepository,
                                               @Lazy TrainerRepository trainerRepository)
    {
        return new UsernameGeneratorImpl(traineeRepository, trainerRepository);
    }

    @Bean
    public PasswordGenerator passwordGenerator(Random random)
    {
        return new PasswordGeneratorImpl(random);
    }

    @Bean
    public Random random()
    {
        return new Random();
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
