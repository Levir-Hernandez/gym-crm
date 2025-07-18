package com.crm.gym.dtos.user;

public interface UserChangePasswordRequest
{
    String getUsername();
    String getOldPassword();
    String getNewPassword();

    void setUsername(String username);
    void setOldPassword(String oldPassword);
    void setNewPassword(String newPassword);
}