package com.crm.gym.dtos.mappers.interfaces;

import com.crm.gym.dtos.trainer.TrainerModificationEmbeddedRequest;
import com.crm.gym.dtos.trainer.TrainerRegistrationRequest;
import com.crm.gym.dtos.trainer.TrainerModificationRequest;
import com.crm.gym.dtos.trainer.TrainerBriefProfile;
import com.crm.gym.dtos.trainer.TrainerCredentials;
import com.crm.gym.dtos.trainer.TrainerProfile;
import com.crm.gym.dtos.trainer.TrainerRef;
import com.crm.gym.entities.Trainer;

public interface TrainerMapper
{
    Trainer toEntity(TrainerRegistrationRequest dto);
    Trainer toEntity(TrainerModificationRequest dto);
    Trainer toEntity(TrainerModificationEmbeddedRequest dto);

    TrainerCredentials toCredentialsDto(Trainer trainer);
    TrainerProfile toProfileDto(Trainer trainer);
    TrainerBriefProfile toBriefProfileDto(Trainer trainer);
    TrainerRef toRefDto(Trainer trainer);
}
