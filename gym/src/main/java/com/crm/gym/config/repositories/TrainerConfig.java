package com.crm.gym.config.repositories;

import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;

import com.crm.gym.repositories.interfaces.TrainerRepository;
import com.crm.gym.entities.Trainer;

@Configuration
@DependsOn("trainingTypeConfig")
public class TrainerConfig extends TemplateConfig<Long, Trainer>
{
    public TrainerConfig(@Value("${storage.trainers.path:}") String trainersPath,
                         TrainerRepository trainerRepository,
                         EntityResourceLoader entityResourceLoader)
    {
        super(trainersPath, trainerRepository, entityResourceLoader);
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}
}
