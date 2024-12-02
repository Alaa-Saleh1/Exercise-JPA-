package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Merchant name must not be empty")
    @Size(min = 3,message = "Merchant name must be at least 3 characters long")
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String name;

    @NotEmpty(message = "City must not be empty")
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String city;
}
