package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.TrainingType;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypeRepositoryImpl extends TemplateRepositoryImpl<Long, TrainingType>
{
    @Override
    protected Class<TrainingType> getEntityClass() {return TrainingType.class;}
}
