package com.crm.gym.repositories.interfaces;

import com.crm.gym.entities.Training;

public interface TrainingRepository extends DynamicQueryRepository, TemplateRepository<Long, Training>
{

}
