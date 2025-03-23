package com.crm.gym.repositories.daos;

import com.crm.gym.entities.Training;
import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.TrainingRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDao extends TemplateDao<Long, Training> implements TrainingRepository
{
    public TrainingDao(TemplateFactory<Long, Training> trainingFactory, Map<Long, Training> trainings)
    {
        super(trainingFactory, trainings);
    }
}
