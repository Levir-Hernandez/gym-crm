package com.crm.gym.config;

import com.crm.gym.entities.*;
import com.crm.gym.util.IdGenerator;
import com.crm.gym.util.PasswordGenerator;
import com.crm.gym.util.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TrainerConfig
{
    private IdGenerator<Long> idGenerator;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    public TrainerConfig(IdGenerator<Long> idGenerator, UsernameGenerator usernameGenerator, PasswordGenerator passwordGenerator)
    {
        this.idGenerator = idGenerator;
        this.usernameGenerator = usernameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    @Bean
    public Trainer trainer1(List<TrainingType> trainingTypes)
    {
        return createTrainer("John", "Doe", true, trainingTypes.get(0));
    }

    @Bean
    public Trainer trainer2(List<TrainingType> trainingTypes)
    {
        return createTrainer("Jane", "Smith", true, trainingTypes.get(1));
    }

    @Bean
    public Trainer trainer3(List<TrainingType> trainingTypes)
    {
        return createTrainer("Mike", "Johnson", true, trainingTypes.get(2));
    }

    @Bean
    public Map<Long, Trainer> trainers(List<Trainer> trainers)
    {
        return trainers.stream().collect(Collectors.toMap(Trainer::getId, trainer -> trainer));
    }

    private Trainer createTrainer(String firstname, String lastname, boolean isActive, TrainingType specialization)
    {
        Trainer trainer = new Trainer(null,
                firstname, lastname,
                null, null,
                isActive, specialization);

        trainer.setId(idGenerator.generateId());

        usernameGenerator.setUser(trainer);
        trainer.setUsername(usernameGenerator.generateUsername());

        trainer.setPassword(passwordGenerator.generatePassword());

        return trainer;
    }
}
