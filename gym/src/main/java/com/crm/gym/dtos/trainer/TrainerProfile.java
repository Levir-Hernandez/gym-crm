package com.crm.gym.dtos.trainer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import com.crm.gym.dtos.trainee.TraineeBriefProfile;

@Getter @Setter
public class TrainerProfile
{
    private String firstname;
    private String lastname;
    private String specialization;
    private Boolean isActive;

    private List<TraineeBriefProfile> trainees;
}
