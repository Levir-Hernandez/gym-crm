package com.crm.gym.entities;

import com.crm.gym.repositories.interfaces.Identifiable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements Identifiable<Long>
{
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Boolean isActive;

    @Override public Long getId() {return id;}
}
