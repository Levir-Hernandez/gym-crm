package com.crm.gym.factories;

import com.crm.gym.entities.Trainer;
import com.crm.gym.util.IdGenerator;
import com.crm.gym.util.PasswordGenerator;
import com.crm.gym.util.UsernameGenerator;
import org.springframework.stereotype.Component;

@Component
public class TrainerFactory implements TemplateFactory<Long, Trainer>
{
    private IdGenerator<Long> idGenerator;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    public TrainerFactory(IdGenerator<Long> idGenerator, UsernameGenerator usernameGenerator, PasswordGenerator passwordGenerator)
    {
        this.idGenerator = idGenerator;
        this.usernameGenerator = usernameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    public Trainer create(String firstname, String lastname, Boolean isActive,
                                 String specialization)
    {
        Trainer trainer = new Trainer(idGenerator.generateId(),
                firstname, lastname,
                null,
                passwordGenerator.generatePassword(),
                isActive, specialization);

        usernameGenerator.setUser(trainer);
        trainer.setUsername(usernameGenerator.generateUsername());

        return trainer;
    }

    public Trainer recreate(Trainer trainer)
    {
        return create(trainer.getFirstname(), trainer.getLastname(),
                trainer.getIsActive(), trainer.getSpecialization());
    }
}
