package com.crm.gym.config.filebased;

import com.crm.gym.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;

@Configuration
public class UtilConfig
{
    @Bean
    @Scope("prototype")
    public IdGenerator<Long> idGenerator()
    {
        return new IdGeneratorImpl(1L);
    }

    @Bean
    public UsernameGenerator usernameGenerator()
    {
        return new UsernameGeneratorImpl();
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
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
