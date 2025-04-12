package com.crm.gym.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import com.crm.gym.entities.Training;
import com.crm.gym.repositories.interfaces.TrainingRepository;
import com.crm.gym.repositories.TrainingQueryCriteria;

@Service
public class TrainingService extends TemplateService<Long, Training, TrainingRepository>
{
    public TrainingService(TrainingRepository repository)
    {
        super(repository);
    }

    public List<Training> getTrainingsByTraineeUsernameAndCriteria(TrainingQueryCriteria trainingQueryCriteria)
    {
        if(Objects.isNull(trainingQueryCriteria.getTraineeUsername())) {return null;}
        return getTrainingsByCriteria(trainingQueryCriteria);
    }

    public List<Training> getTrainingsByTrainerUsernameAndCriteria(TrainingQueryCriteria trainingQueryCriteria)
    {
        if(Objects.isNull(trainingQueryCriteria.getTrainerUsername())) {return null;}
        return getTrainingsByCriteria(trainingQueryCriteria);
    }

    public List<Training> getTrainingsByCriteria(TrainingQueryCriteria trainingQueryCriteria)
    {
        return repository.findTrainingsByCriteria(trainingQueryCriteria);
    }
}
