package com.crm.gym.repositories.daos;

import com.crm.gym.entities.Trainer;
import com.crm.gym.repositories.interfaces.TrainerRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDao extends TemplateDao<Long, Trainer> implements TrainerRepository
{
    public TrainerDao(Map<Long, Trainer> trainers)
    {
        super(trainers);
    }
}
