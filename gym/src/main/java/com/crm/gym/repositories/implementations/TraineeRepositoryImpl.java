package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.Trainee;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TraineeRepositoryImpl extends TemplateRepositoryImpl<UUID, Trainee>
{
    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}
}
