package com.crm.gym.services;

import org.springframework.stereotype.Service;

import java.util.Set;
import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.TraineeRepository;

@Service
public class TraineeService extends UserService<Trainee, TraineeRepository>
{
    public TraineeService(TraineeRepository repository)
    {
        super(repository);
    }

    @Override
    public Trainee updateEntity(Long idTrainee, Trainee trainee)
    {
        return super.updateEntity(idTrainee, trainee);
    }

    @Override
    public boolean deleteEntity(Long entityId)
    {
        return super.deleteEntity(entityId);
    }

    public boolean deleteTraineeByUsername(String username)
    {
        return repository.deleteByUsernameIfExists(username);
    }

    public int updateAssignedTrainersForTrainee(String traineeUsername, Set<Trainer> trainers)
    {
        return repository.updateAssignedTrainersForTrainee(traineeUsername, trainers);
    }
}
