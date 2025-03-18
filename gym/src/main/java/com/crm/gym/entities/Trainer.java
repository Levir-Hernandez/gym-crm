package com.crm.gym.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User
{
    private TrainingType specialization;

    public Trainer(Long id, String firstname, String lastname, String username, String password, Boolean isActive, TrainingType specialization)
    {
        super(id, firstname, lastname, username, password, isActive);
        this.specialization = specialization;
    }
}
