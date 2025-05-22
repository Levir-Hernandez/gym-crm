package com.crm.gym.dtos.trainee;

import com.crm.gym.dtos.trainer.TrainerBriefProfile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.time.LocalDate;

@Getter @Setter
public class TraineeProfile
{
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private Boolean isActive;

    private List<TrainerBriefProfile> trainers;
}
