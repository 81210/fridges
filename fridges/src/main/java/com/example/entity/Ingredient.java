package com.example.fridges.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    private String name; // 이름
    private int quantity; // 갯수
    private String unit; //단위 ex) 개
    private LocalDate expiryDate; //유통기한
    private String location; //위치
    private String memo; // 메모
    private String status; //상태
}