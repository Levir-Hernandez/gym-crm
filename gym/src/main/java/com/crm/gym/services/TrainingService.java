package com.crm.gym.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import com.crm.gym.entities.Training;
import com.crm.gym.repositories.TrainingQueryCriteria;
import com.crm.gym.repositories.interfaces.TrainingRepository;

@Service
public class TrainingService extends TemplateService<Long, Training, TrainingRepository>
{
    public TrainingService(TrainingRepository repository)
    {
        super(repository);
    }

    public List<Training> getTrainingsByCriteria(TrainingQueryCriteria trainingQueryCriteria)
    {
        return repository.findByCriteria(trainingQueryCriteria);
    }

    public Page<Training> getTrainingsByCriteria(TrainingQueryCriteria trainingQueryCriteria, Pageable pageable)
    {
        return repository.findByCriteria(trainingQueryCriteria, pageable);
    }
}
