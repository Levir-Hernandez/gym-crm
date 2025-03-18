package com.crm.gym.config;

import java.util.Random;
import com.crm.gym.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Configuration;

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
    public PasswordGenerator passwordGenerator()
    {
        return new PasswordGeneratorImpl(new Random());
    }
}
