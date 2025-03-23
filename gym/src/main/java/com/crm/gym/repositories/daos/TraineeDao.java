package com.crm.gym.repositories.daos;

import java.util.Map;
import com.crm.gym.entities.Trainee;
import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.TraineeRepository;

import org.springframework.stereotype.Repository;

@Repository
public class TraineeDao extends TemplateDao<Long, Trainee> implements TraineeRepository
{
    public TraineeDao(TemplateFactory<Long, Trainee> traineeFactory, Map<Long, Trainee> trainees)
    {
        super(traineeFactory, trainees);
    }

    @Override
    public long countByUsernameLike(String username)
    {
        return getEntities().values().stream()
                .filter(trainee -> trainee.getUsername().contains(username))
                .count();
    }
}
