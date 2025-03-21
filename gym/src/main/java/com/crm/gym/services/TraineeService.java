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
    public void updateEntity(Trainee trainee)
    {
        super.updateEntity(trainee);
    }

    @Override
    public void deleteEntity(Long entityId)
    {
        super.deleteEntity(entityId);
    }
}
