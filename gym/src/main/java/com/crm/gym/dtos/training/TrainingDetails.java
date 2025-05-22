package com.crm.gym.dtos.training;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class TrainingDetails
{
    private String name;
    private String trainingType;
    private LocalDate date;
    private Integer duration;
    private String trainerUsername;
    private String traineeUsername;
}
