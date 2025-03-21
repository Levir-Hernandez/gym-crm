package com.crm.gym.entities;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Date;
import com.crm.gym.repositories.interfaces.Identifiable;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training implements Identifiable<Long>
{
    private Long id;
    private String name;
    private Date date;
    private Integer duration;

    private Trainee trainee;
    private Trainer trainer;
    private TrainingType type;

    @Override public Long getId() {return id;}
}
