package com.crm.gym.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ToString(callSuper = true, exclude = "trainings")
@EqualsAndHashCode(callSuper = true, exclude = "trainings")

@Entity
@Table(name="trainees")
public class Trainee extends User
{
    private LocalDate birthdate;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE)
    private List<Training> trainings;

    public Trainee(Long id, String firstname, String lastname, String username, String password, Boolean isActive, LocalDate birthdate, String address)
    {
        super(id, firstname, lastname, username, password, isActive);
        this.birthdate = birthdate;
        this.address = address;
    }

    public Trainee(Long id) {super(id);}
}
