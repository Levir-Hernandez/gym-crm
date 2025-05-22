package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.Trainee;
import com.crm.gym.factories.TraineeFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeRepositoryImpl extends TemplateRepositoryImpl<Long, Trainee>
{
    private TraineeFactory traineeFactory;

    public TraineeRepositoryImpl(TraineeFactory traineeFactory)
    {
        this.traineeFactory = traineeFactory;
    }

    @Override
    protected Class<Trainee> getEntityClass() {return Trainee.class;}

    @Override
    public Trainee create(Trainee trainee)
    {
        trainee = traineeFactory.recreate(trainee);
        return super.create(trainee);
    }
}
