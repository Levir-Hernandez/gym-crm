package com.crm.gym.factories;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import com.crm.gym.util.IdGenerator;
import com.crm.gym.entities.Trainee;
import com.crm.gym.util.PasswordGenerator;
import com.crm.gym.util.UsernameGenerator;

@Component
public class TraineeFactory implements TemplateFactory<Long, Trainee>
{
    private IdGenerator<Long> idGenerator;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    public TraineeFactory(IdGenerator<Long> idGenerator, UsernameGenerator usernameGenerator, PasswordGenerator passwordGenerator)
    {
        this.idGenerator = idGenerator;
        this.usernameGenerator = usernameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    public Trainee create(String firstname, String lastname, Boolean isActive,
                                 LocalDate birthdate, String address)
    {
        Trainee trainee = new Trainee(idGenerator.generateId(),
                firstname, lastname,
                null,
                passwordGenerator.generatePassword(),
                isActive, birthdate, address);

        usernameGenerator.setUser(trainee);
        trainee.setUsername(usernameGenerator.generateUsername());

        return trainee;
    }

    public Trainee recreate(Trainee trainee)
    {
        return create(trainee.getFirstname(), trainee.getLastname(),
                trainee.getIsActive(), trainee.getBirthdate(), trainee.getAddress());
    }
}
