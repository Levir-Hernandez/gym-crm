package com.crm.gym.entities;

import java.time.LocalDate;
import com.crm.gym.repositories.interfaces.Identifiable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training implements Identifiable<Long>
{
    private Long id;
    private String name;
    private LocalDate date;
    private Integer duration;

    private Long traineeId;
    private Long trainerId;
    private Long trainingTypeId;

    @Override public Long getId() {return id;}
}
