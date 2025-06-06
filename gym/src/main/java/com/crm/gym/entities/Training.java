package com.crm.gym.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.crm.gym.repositories.interfaces.Identifiable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="trainings")
public class Training implements Identifiable<Long>
{
    @Id @GeneratedValue
    private Long id;

    private String name;
    private LocalDate date;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "trainee_fk")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_fk")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "training_type_fk")
    private TrainingType trainingType;

    public Training(Long id) {this.id = id;}
}
