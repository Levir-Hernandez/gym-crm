package com.crm.gym.services;

import com.crm.gym.entities.Trainee;
import org.springframework.stereotype.Service;
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
}
