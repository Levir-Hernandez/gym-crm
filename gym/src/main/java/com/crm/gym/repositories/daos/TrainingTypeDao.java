package com.crm.gym.repositories.daos;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingTypeDao extends TemplateDao<Long, TrainingType> implements TrainingTypeRepository
{
    public TrainingTypeDao(TemplateFactory<Long, TrainingType> trainingTypeFactory, Map<Long, TrainingType> trainingTypes)
    {
        super(trainingTypeFactory, trainingTypes);
    }
}
