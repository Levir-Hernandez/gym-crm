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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        name="users",
        indexes = {@Index(name = "users_username_idx", columnList = "username", unique = true)}
)
public abstract class User implements Identifiable<Long>
{
    @Id @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Boolean isActive;

    public User(Long id) {this.id = id;}
    public User(String username) {this.username = username;}
}
