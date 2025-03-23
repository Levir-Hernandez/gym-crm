package com.crm.gym.repositories.daos;

import java.util.Map;
import com.crm.gym.entities.Trainer;
import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.TrainerRepository;

import org.springframework.stereotype.Repository;

@Repository
public class TrainerDao extends TemplateDao<Long, Trainer> implements TrainerRepository
{
    public TrainerDao(TemplateFactory<Long, Trainer> trainerFactory, Map<Long, Trainer> trainers)
    {
        super(trainerFactory, trainers);
    }

    @Override
    public long countByUsernameLike(String username)
    {
        return getEntities().values().stream()
                .filter(trainer -> trainer.getUsername().contains(username))
                .count();
    }
}
