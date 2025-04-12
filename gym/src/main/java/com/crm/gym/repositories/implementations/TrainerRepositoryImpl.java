package com.crm.gym.repositories.implementations;

import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.factories.TrainerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainerRepositoryImpl extends TemplateRepositoryImpl<Long, Trainer>
{
    private TrainerFactory trainerFactory;

    public TrainerRepositoryImpl(TrainerFactory trainerFactory)
    {
        this.trainerFactory = trainerFactory;
    }

    @Override
    protected Class<Trainer> getEntityClass() {return Trainer.class;}

    @Override
    public Trainer create(Trainer trainer)
    {
        nullifyInvalidReferences(trainer);
        trainer = trainerFactory.recreate(trainer);
        return super.create(trainer);
    }

    @Override
    public Trainer update(Long entityId, Trainer trainer)
    {
        nullifyInvalidReferences(trainer);
        return super.update(entityId, trainer);
    }

    private void nullifyInvalidReferences(Trainer trainer)
    {
        boolean invalidSpecialization = Optional
                .ofNullable(trainer.getSpecialization())
                .map(TrainingType::getId)
                .map(id -> em.find(TrainingType.class, id))
                .isEmpty();

        if(invalidSpecialization) {trainer.setSpecialization(null);}
    }
}
