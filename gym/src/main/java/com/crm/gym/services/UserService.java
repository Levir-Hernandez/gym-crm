package com.crm.gym.services;

import java.util.Objects;
import com.crm.gym.entities.User;
import com.crm.gym.repositories.interfaces.UserRepository;

public abstract class UserService<S extends User, R extends UserRepository<S>>
        extends TemplateService<Long, S, R>
{
    public UserService(R repository)
    {
        super(repository);
    }

    public S updateUserByUsername(String username, S user)
    {
        return repository.updateByUsername(username, user);
    }

    public S getUserByUsername(String username)
    {
        return repository.findByUsername(username).orElse(null);
    }

    public boolean login(String username, String password)
    {
        return repository.existsByUsernameAndPassword(username, password);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword)
    {
        S user = repository.findByUsername(username).orElse(null);

        if(Objects.isNull(user) || !user.getPassword().equals(oldPassword)) {return false;}

        user.setPassword(newPassword);
        repository.save(user);
        return true;
    }

    public Boolean activateUser(String username)
    {
        return updateUserIsActive(username, true);
    }

    public Boolean deactivateUser(String username)
    {
        return updateUserIsActive(username, false);
    }

    private Boolean updateUserIsActive(String username, Boolean isActive)
    {
        S user = getUserByUsername(username);
        if(Objects.isNull(user)){return null;}

        Boolean isActiveHasChanged = !user.getIsActive().equals(isActive);
        if(isActiveHasChanged)
        {
            user.setIsActive(isActive);
            repository.save(user);
        }

        return isActiveHasChanged;
    }
}
