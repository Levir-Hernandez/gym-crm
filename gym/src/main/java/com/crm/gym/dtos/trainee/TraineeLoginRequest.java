package com.crm.gym.dtos.trainee;

import com.crm.gym.dtos.user.UserLoginRequest;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class TraineeLoginRequest implements UserLoginRequest
{
    @NotNull(message = "Trainee username is required")
    private String username;

    @NotNull(message = "Trainee password is required")
    private String password;
}
