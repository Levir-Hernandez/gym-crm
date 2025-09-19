package com.crm.gym.config.entities;

import com.crm.gym.services.TrainingTypeService;
import com.crm.gym.util.EntityResourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import com.crm.gym.entities.TrainingType;

import java.util.UUID;

@Configuration
public class TrainingTypeConfig extends TemplateConfig<UUID, TrainingType, TrainingTypeRepository>
{
    public TrainingTypeConfig(@Value("${storage.training-types.path:}") String trainingTypesPath,
                              TrainingTypeService trainingTypeService,
                              EntityResourceLoader entityResourceLoader)
    {
        super(trainingTypesPath, trainingTypeService, entityResourceLoader);
    }

    @Override
    protected Class<TrainingType> getEntityClass() {return TrainingType.class;}

    @Override
    protected boolean createEntitiesFromJson()
    {
        boolean createdFromJson = super.createEntitiesFromJson();
        if(entityService.getEntitiesCount() < 1 && !createdFromJson)
        {
            throw new UnavailableTrainingTypesException();
        }
        return createdFromJson;
    }

    private static class UnavailableTrainingTypesException extends RuntimeException
    {
        public UnavailableTrainingTypesException()
        {
            super("TrainingTypes missing: none found in the database and no valid entity source provided");
        }
    }
}
