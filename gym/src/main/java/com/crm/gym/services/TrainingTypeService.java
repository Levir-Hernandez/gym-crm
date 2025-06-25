package com.crm.gym.services;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeService extends TemplateService<Long, TrainingType, TrainingTypeRepository>
{
    public TrainingTypeService(TrainingTypeRepository repository)
    {
        super(repository);
    }
}
