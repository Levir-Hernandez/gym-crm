package com.crm.gym.repositories.interfaces;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.crm.gym.entities.User;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<S extends User> extends TemplateRepository<Long, S>
{
    Optional<S> findByUsername(String username);
    List<S> findByUsernameStartsWith(String prefix);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);

    @Transactional
    default S updateByUsername(String username, S user)
    {
        Long id = findByUsername(username).map(User::getId).orElse(null);

        if(Objects.nonNull(id))
        {
            user.setId(id);
            user.setUsername(username);
            user = save(user);
        }
        else {user = null;}

        return user;
    }
}
