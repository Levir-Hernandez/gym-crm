package com.crm.gym.services;

import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.TraineeRepository;

import org.springframework.stereotype.Service;

@Service
public class TraineeService extends TemplateService<Long, Trainee, TraineeRepository>
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
    public Trainee deleteEntity(Long entityId)
    {
        return super.deleteEntity(entityId);
    }
}
