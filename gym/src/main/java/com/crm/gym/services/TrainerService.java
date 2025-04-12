package com.crm.gym.services;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TrainerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService extends UserService<Trainer, TrainerRepository>
{
    public TrainerService(TrainerRepository repository)
    {
        super(repository);
    }

    @Override
    public Trainer updateEntity(Long idTrainer, Trainer trainer)
    {
        return super.updateEntity(idTrainer, trainer);
    }

    public List<Trainer> getAllUnassignedForTraineeByUsername(String username)
    {
        return repository.findAllUnassignedForTraineeByUsername(username);
    }
}
