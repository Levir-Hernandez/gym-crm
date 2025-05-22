package com.crm.gym.dtos.trainer;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class TrainerModificationRequest  extends TrainerRegistrationRequest
{
    @NotNull(message = "Trainer isActive is required")
    private Boolean isActive;
}
