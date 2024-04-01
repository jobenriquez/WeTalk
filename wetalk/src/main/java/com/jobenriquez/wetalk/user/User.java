package com.jobenriquez.wetalk.user;

/**
 * @author Isaiah Job Cuenca Enriquez
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String profilePicture = null;
    private boolean isActive = false;
}
