package com.crm.gym.dtos.mappers.interfaces;

import com.crm.gym.dtos.trainee.*;
import com.crm.gym.entities.Trainee;

public interface TraineeMapper
{
    Trainee toEntity(TraineeRegistrationRequest dto);
    Trainee toEntity(TraineeModificationRequest dto);

    TraineeCredentials toCredentialsDto(Trainee trainee);
    TraineeProfile toProfileDto(Trainee trainee);
    TraineeBriefProfile toBriefProfileDto(Trainee trainee);
    TraineeRef toRefDto(Trainee trainee);
}
