package com.crm.gym.repositories.daos;

import com.crm.gym.entities.TrainingType;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingTypeDao extends TemplateDao<Long, TrainingType>
{
    public TrainingTypeDao(Map<Long, TrainingType> trainingTypes)
    {
        super(trainingTypes);
    }
}
