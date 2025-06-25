package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.Trainee;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeRepositoryImpl extends TemplateRepositoryImpl<Long, Trainee>
{
    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}
}
