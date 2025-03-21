package com.crm.gym.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Trainee extends User
{
    private Date birthdate;
    private String address;

    public Trainee(Long id, String firstname, String lastname, String username, String password, Boolean isActive, Date birthdate, String address)
    {
        super(id, firstname, lastname, username, password, isActive);
        this.birthdate = birthdate;
        this.address = address;
    }
}
