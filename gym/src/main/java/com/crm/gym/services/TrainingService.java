package com.crm.gym.services;

import com.crm.gym.entities.Training;
import com.crm.gym.repositories.interfaces.TrainingRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingService extends TemplateService<Long, Training, TrainingRepository>
{
    public TrainingService(TrainingRepository repository)
    {
        super(repository);
    }
}
