package com.crm.gym.entities;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User
{
    private Long specialization;

    public Trainer(Long id, String firstname, String lastname, String username, String password, Boolean isActive, Long specialization)
    {
        super(id, firstname, lastname, username, password, isActive);
        this.specialization = specialization;
    }
}
