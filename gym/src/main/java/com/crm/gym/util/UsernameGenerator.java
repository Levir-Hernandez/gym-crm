package com.crm.gym.util;

import com.crm.gym.entities.User;

public interface UsernameGenerator
{
    void setUser(User user);
    String generateUsername();
}
