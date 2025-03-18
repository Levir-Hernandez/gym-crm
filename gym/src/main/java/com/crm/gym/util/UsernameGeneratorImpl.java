package com.crm.gym.util;

import com.crm.gym.entities.User;

import lombok.Setter;

@Setter
public class UsernameGeneratorImpl implements UsernameGenerator
{
    private User user;

    @Override
    public String generateUsername()
    {
        return user.getFirstname() + "." + user.getLastname();
    }
}
