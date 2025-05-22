package com.crm.gym.dtos.mappers.interfaces;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.dtos.trainingType.TrainingTypeDto;

public interface TrainingTypeMapper
{
    TrainingTypeDto toDto(TrainingType trainingType);
}
