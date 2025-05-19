package com.crm.gym.config.security;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.crm.gym.repositories.interfaces.TraineeRepository;
import com.crm.gym.repositories.interfaces.TrainerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;

    public UserDetailsServiceImpl(TraineeRepository traineeRepository, TrainerRepository trainerRepository)
    {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        com.crm.gym.entities.User user = Stream.of(traineeRepository, trainerRepository)
                .map(r -> r.findByUsername(username))
                .filter(Optional::isPresent).map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}