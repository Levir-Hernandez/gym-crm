package com.crm.gym.services;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrainingTypeService extends TemplateService<UUID, TrainingType, TrainingTypeRepository>
{
    public TrainingTypeService(TrainingTypeRepository repository)
    {
        super(repository);
    }
}
