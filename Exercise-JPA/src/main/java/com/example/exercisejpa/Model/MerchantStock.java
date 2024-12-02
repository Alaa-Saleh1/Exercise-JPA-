package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Product ID must not be empty")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer productId;

    @NotNull(message = "Merchant ID must not be empty")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer merchantId;

    @NotNull(message = "Stock quantity must not be null")
    @Min(value = 10, message = "Stock quantity must be at least 10")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer stock ;
}
