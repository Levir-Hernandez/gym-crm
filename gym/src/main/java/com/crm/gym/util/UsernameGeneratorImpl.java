package com.crm.gym.util;

import lombok.Setter;
import com.crm.gym.entities.User;
import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.repositories.interfaces.TrainerRepository;

public class UsernameGeneratorImpl implements UsernameGenerator
{
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;

    @Setter private User user;

    public UsernameGeneratorImpl(TraineeRepository traineeRepository, TrainerRepository trainerRepository)
    {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public String generateUsername()
    {
        String username = user.getFirstname() + "." + user.getLastname();

        long counter = traineeRepository.countByUsernameLike(username) + trainerRepository.countByUsernameLike(username);
        if(counter > 0){username += counter;}

        return username;
    }
}
