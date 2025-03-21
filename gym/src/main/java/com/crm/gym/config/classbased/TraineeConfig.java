package com.crm.gym.config.classbased;

import java.util.Map;
import java.util.Date;
import java.util.List;
import com.crm.gym.entities.User;
import com.crm.gym.entities.Trainee;
import java.util.stream.Collectors;

import com.crm.gym.util.IdGenerator;
import com.crm.gym.util.UsernameGenerator;
import com.crm.gym.util.PasswordGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraineeConfig
{
    private IdGenerator<Long> idGenerator;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    public TraineeConfig(IdGenerator<Long> idGenerator, UsernameGenerator usernameGenerator, PasswordGenerator passwordGenerator)
    {
        this.idGenerator = idGenerator;
        this.usernameGenerator = usernameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    @Bean
    public Trainee trainee1()
    {
        return createTrainee("Alice", "Smith",
                true, new Date(90, 5, 15), "123 Main St");
    }

    @Bean
    public Trainee trainee2()
    {
        return createTrainee("Bob", "Johnson",
                false, new Date(85, 10, 20), "456 Elm St");
    }

    @Bean
    public Trainee trainee3()
    {
        return createTrainee("Charlie", "Brown",
                true, new Date(95, 2, 8), "789 Oak St");
    }

    @Bean
    public Map<Long, Trainee> trainees(List<Trainee> trainees)
    {
        return trainees.stream().collect(Collectors.toMap(User::getId, trainee -> trainee));
    }

    private Trainee createTrainee(String firstname, String lastname, boolean isActive, Date birthdate, String address)
    {
        Trainee trainee = new Trainee(null,
                firstname, lastname,
                null, null,
                isActive, birthdate, address);

        trainee.setId(idGenerator.generateId());

        usernameGenerator.setUser(trainee);
        trainee.setUsername(usernameGenerator.generateUsername());

        trainee.setPassword(passwordGenerator.generatePassword());

        return trainee;
    }
}
