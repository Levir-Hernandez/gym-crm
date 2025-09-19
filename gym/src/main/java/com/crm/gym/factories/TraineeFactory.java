package com.crm.gym.factories;

import java.time.LocalDate;
import java.util.UUID;

import com.crm.gym.entities.Trainee;
import com.crm.gym.util.PasswordGenerator;
import com.crm.gym.util.UsernameGenerator;
import org.springframework.stereotype.Component;

@Component
public class TraineeFactory implements UserFactory<UUID, Trainee>
{
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    public TraineeFactory(UsernameGenerator usernameGenerator, PasswordGenerator passwordGenerator)
    {
        this.usernameGenerator = usernameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    public Trainee create(String firstname, String lastname, Boolean isActive,
                                 LocalDate birthdate, String address)
    {
        Trainee trainee = new Trainee(null, // will be set by JPA
                firstname, lastname,
                null,
                passwordGenerator.generatePassword(),
                isActive, birthdate, address);

        usernameGenerator.setUser(trainee);
        trainee.setUsername(usernameGenerator.generateUsername());

        return trainee;
    }

    @Override
    public Trainee recreate(Trainee trainee)
    {
        return create(trainee.getFirstname(), trainee.getLastname(),
                trainee.getIsActive(), trainee.getBirthdate(), trainee.getAddress());
    }
}
