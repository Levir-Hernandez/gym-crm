package com.crm.gym.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.crm.gym.repositories.interfaces.Identifiable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name="training_types",
        indexes = {@Index(name = "training_types_name_idx", columnList = "name", unique = true)}
)
public class TrainingType implements Identifiable<Long>
{
    @Id @GeneratedValue
    private Long id;

    private String name;

    public TrainingType(Long id) {this.id = id;}
    public TrainingType(String name) {this.name = name;}
}
