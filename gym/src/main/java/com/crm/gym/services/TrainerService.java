package com.crm.gym.services;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.repositories.interfaces.TrainerRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainerService extends UserService<Trainer, TrainerRepository>
{
    private TraineeRepository traineeRepository;

    public TrainerService(TrainerRepository repository, TraineeRepository traineeRepository)
    {
        super(repository);
        this.traineeRepository = traineeRepository;
    }

    @Override
    public Trainer updateEntity(Long idTrainer, Trainer trainer)
    {
        return super.updateEntity(idTrainer, trainer);
    }

    public List<Trainer> getAllUnassignedForTraineeByUsername(String username)
    {
        return Optional.of(username)
                .filter(traineeRepository::existsByUsername)
                .map(repository::findAllUnassignedForTraineeByUsername)
                .orElse(null);
    }

    public Integer updateAssignedTrainersForTrainee(String traineeUsername, Set<Trainer> trainers)
    {
        return Optional.of(traineeUsername)
                .filter(traineeRepository::existsByUsername)
                .map(validTrainee -> repository.updateAssignedTrainersForTrainee(validTrainee, trainers))
                .orElse(null);
    }
}
