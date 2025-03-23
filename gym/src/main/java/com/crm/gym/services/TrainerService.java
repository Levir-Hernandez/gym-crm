package com.crm.gym.services;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TrainerRepository;

import org.springframework.stereotype.Service;

@Service
public class TrainerService extends TemplateService<Long, Trainer, TrainerRepository>
{
    public TrainerService(TrainerRepository repository)
    {
        super(repository);
    }

    @Override
    public void updateEntity(Long idTrainer, Trainer trainer)
    {
        super.updateEntity(idTrainer, trainer);
    }
}
