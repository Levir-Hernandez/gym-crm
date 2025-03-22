package com.crm.gym.factories;

import org.springframework.stereotype.Component;

import com.crm.gym.util.IdGenerator;
import com.crm.gym.entities.TrainingType;

@Component
public class TrainingTypeFactory implements TemplateFactory<Long, TrainingType>
{
    private IdGenerator<Long> idGenerator;

    public TrainingTypeFactory(IdGenerator<Long> idGenerator)
    {
        this.idGenerator = idGenerator;
    }

    public TrainingType create(String name)
    {
        return new TrainingType(idGenerator.generateId(), name);
    }

    public TrainingType recreate(TrainingType trainingType)
    {
        return create(trainingType.getName());
    }
}
