package com.crm.gym.config.entities;

import com.crm.gym.services.TrainerService;
import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;

import com.crm.gym.repositories.interfaces.TrainerRepository;
import com.crm.gym.entities.Trainer;

import java.util.UUID;

@Configuration
@DependsOn("trainingTypeConfig")
public class TrainerConfig extends TemplateConfig<UUID, Trainer, TrainerRepository>
{
    public TrainerConfig(@Value("${storage.trainers.path:}") String trainersPath,
                         TrainerService trainerService,
                         EntityResourceLoader entityResourceLoader)
    {
        super(trainersPath, trainerService, entityResourceLoader);
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}
}
