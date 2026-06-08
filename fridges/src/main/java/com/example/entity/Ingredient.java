package com.example.fridges.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ingredient")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    private String unit;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    private String location;

    private String memo;

    private String status;
}
