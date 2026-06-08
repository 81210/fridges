package com.example.fridges.repository;

import com.example.fridges.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
// interface = 구현 없이 기능만 선언하는 것
// extends JpaRepository<Ingredient, Long>
//   → Ingredient 테이블을 대상으로
//   → id 타입은 Long (숫자)
//   → findAll, save, deleteById 등 자동 생성

}
// 비어있지만 JpaRepository 덕분에 CRUD 다 됨