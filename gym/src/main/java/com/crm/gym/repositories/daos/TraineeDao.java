package com.crm.gym.repositories.daos;

import com.crm.gym.entities.Trainee;
import com.crm.gym.repositories.interfaces.TraineeRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDao extends TemplateDao<Long, Trainee> implements TraineeRepository
{
    public TraineeDao(Map<Long, Trainee> trainees)
    {
        super(trainees);
    }
}
