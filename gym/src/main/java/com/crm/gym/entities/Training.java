package com.crm.gym.entities;

import lombok.Data;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.crm.gym.repositories.interfaces.Identifiable;

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
