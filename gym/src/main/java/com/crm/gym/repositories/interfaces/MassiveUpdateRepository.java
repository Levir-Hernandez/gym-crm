package com.crm.gym.repositories.interfaces;

import java.util.Set;
import com.crm.gym.entities.Trainer;

public interface MassiveUpdateRepository
{
        int updateAssignedTrainersForTrainee(String traineeUsername, Set<Trainer> trainers);
}
