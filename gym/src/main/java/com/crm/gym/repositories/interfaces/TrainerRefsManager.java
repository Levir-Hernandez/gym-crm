package com.crm.gym.repositories.interfaces;

import com.crm.gym.entities.Trainer;

public interface TrainerRefsManager
{
    void resolveReferencesByAltKeys(Trainer trainer);
    void nullifyInvalidReferences(Trainer trainer);
}
