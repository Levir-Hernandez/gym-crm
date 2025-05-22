package com.crm.gym.dtos.mappers.implementations;

import com.crm.gym.entities.TrainingType;
import org.springframework.stereotype.Component;
import com.crm.gym.dtos.trainingType.TrainingTypeDto;
import com.crm.gym.dtos.mappers.interfaces.TrainingTypeMapper;

@Component
public class TrainingTypeMapperImpl implements TrainingTypeMapper
{
    @Override
    public TrainingTypeDto toDto(TrainingType trainingType)
    {
        TrainingTypeDto dto = new TrainingTypeDto();
        dto.setName(trainingType.getName());
        return dto;
    }
}
