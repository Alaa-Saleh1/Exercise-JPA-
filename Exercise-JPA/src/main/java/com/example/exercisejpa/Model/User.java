package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 5, message = "Username must be at least 5 characters long")
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6,message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$",message = "Password must have characters and digits")
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String password;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String email;

    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "Admin|Customer",message = "Role must be either 'Admin' or 'Customer'.")
    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String role;

    @NotNull(message = "Balance must not be empty")
    @Positive(message = "Balance must be a positive number")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer balance;

    private Integer counter;

    private Boolean isPrime ;


}
