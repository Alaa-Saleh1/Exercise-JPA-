package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product Name must not be empty")
    @Size(min = 3,message = "Product name must be at least 3 characters long")
    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String name;

    @NotNull(message = "Product price must not be empty")
    @Positive(message = "Product price must be positive number")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer price;

    @NotNull(message = "Category ID must not be empty")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer categoryID;

    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
    private LocalDate dateAdded;

}
