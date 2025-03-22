package com.crm.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import com.crm.gym.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
