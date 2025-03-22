package com.crm.gym.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Trainee extends User
{
    private LocalDate birthdate;
    private String address;

    public Trainee(Long id, String firstname, String lastname, String username, String password, Boolean isActive, LocalDate birthdate, String address)
    {
        super(id, firstname, lastname, username, password, isActive);
        this.birthdate = birthdate;
        this.address = address;
    }
}
