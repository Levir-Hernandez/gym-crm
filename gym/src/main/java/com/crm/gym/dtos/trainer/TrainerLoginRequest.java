package com.crm.gym.dtos.trainer;

import com.crm.gym.dtos.user.UserLoginRequest;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class TrainerLoginRequest implements UserLoginRequest
{
    @NotNull(message = "Trainer username is required")
    private String username;

    @NotNull(message = "Trainer password is required")
    private String password;
}
