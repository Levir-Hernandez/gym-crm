package com.crm.gym.factories;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import com.crm.gym.util.IdGenerator;
import com.crm.gym.entities.Training;

@Component
public class TrainingFactory implements TemplateFactory<Long, Training>
{
    private IdGenerator<Long> idGenerator;

    public TrainingFactory(IdGenerator<Long> idGenerator)
    {
        this.idGenerator = idGenerator;
    }

    public Training create(String name, LocalDate date, Integer duration, Long traineeId, Long trainerId, Long trainingTypeId)
    {
        return new Training(idGenerator.generateId(),
                name, date, duration,
                traineeId, trainerId, trainingTypeId);
    }

    public Training recreate(Training training)
    {
        return create(training.getName(), training.getDate(), training.getDuration(),
                training.getTrainerId(), training.getTrainerId(), training.getTrainingTypeId());
    }
}
