package com.crm.gym.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.crm.gym.repositories.interfaces.Identifiable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingType implements Identifiable<Long>
{
    private Long id;
    private String name;

    @Override public Long getId() {return id;}
}
