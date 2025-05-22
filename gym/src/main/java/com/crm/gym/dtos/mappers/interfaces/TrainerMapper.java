package com.crm.gym.dtos.mappers.interfaces;

import com.crm.gym.dtos.trainer.*;
import com.crm.gym.entities.Trainer;

public interface TrainerMapper
{
    Trainer toEntity(TrainerRegistrationRequest dto);
    Trainer toEntity(TrainerModificationRequest dto);
    Trainer toEntity(TrainerModificationEmbeddedRequest dto);

    TrainerCredentials toCredentialsDto(Trainer trainer);
    TrainerProfile toProfileDto(Trainer trainer);
    TrainerBriefProfile toBriefProfileDto(Trainer trainer);
}
