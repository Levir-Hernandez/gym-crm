package com.crm.gym.entities;

import com.crm.gym.repositories.interfaces.Identifiable;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TrainingType implements Identifiable<Long>
{
    private Long id;
    private String name;

    @Override public Long getId() {return id;}
}
