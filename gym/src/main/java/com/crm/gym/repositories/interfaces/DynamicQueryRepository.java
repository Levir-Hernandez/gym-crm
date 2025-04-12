package com.crm.gym.repositories.interfaces;

import com.crm.gym.entities.Training;
import com.crm.gym.repositories.TrainingQueryCriteria;

import java.util.List;

public interface DynamicQueryRepository
{
    List<Training> findTrainingsByCriteria(TrainingQueryCriteria criteria);
}
