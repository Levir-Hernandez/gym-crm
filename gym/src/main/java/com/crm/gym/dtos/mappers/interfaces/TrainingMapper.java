package com.crm.gym.dtos.mappers.interfaces;

import com.crm.gym.entities.Training;
import com.crm.gym.dtos.training.TrainingDetails;
import com.crm.gym.dtos.training.TrainingScheduleRequest;

public interface TrainingMapper
{
    Training toEntity(TrainingScheduleRequest dto);
    TrainingDetails toDetailsDto(Training training);
}
