package com.example.fridges.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ingredient") // DB 테이블명
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id 자동 생성
    private Long id;  // id

    private String name; // 이름

    private int quantity; // 수량

    private String unit; // 단위 ex 몇개 몇g 등

    @Column(name = "expiry_date")
    private LocalDate expiryDate;  // 유통기한

    private String location; // 냉장고 위치 ex 냉동 냉장 등

    private String memo;  // 메모

    private String status; // 상태 ex 여유 임박 등
}
