package com.example.fridges.controller;

import com.example.fridges.dto.ErrorResponse;
import com.example.fridges.entity.Ingredient;
import com.example.fridges.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 플루터연동용
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping   //   조회
    public ResponseEntity<List<Ingredient>> getAll() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    @GetMapping("/{id}")  // 조회
    public ResponseEntity<?> getById(@PathVariable Long id) {  // id 찾아줘요 라는 명령
        try {
            return ResponseEntity.ok(ingredientService.getById(id));    // id 찾아줘요 라는 요청이 성공했을 때
        } catch (RuntimeException e) {   // 요청 실패, 에러나면 서비스에서 여기로 줌 
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("INGREDIENT_NOT_FOUND", e.getMessage(), 404)); // 404, not found 응답
        }  // ErrorResponse 형식으로 Flutter에 반환
    }

    @PostMapping   // 추가
    public ResponseEntity<Ingredient> create(@RequestBody Ingredient ingredient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.create(ingredient));
    }

    @PutMapping("/{id}")  // 수정
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.update(id, ingredient));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("INGREDIENT_NOT_FOUND", e.getMessage(), 404));
        }
    }

    @DeleteMapping("/{id}")  // 삭제
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            ingredientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("INGREDIENT_NOT_FOUND", e.getMessage(), 404));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(ingredientService.updateStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("INGREDIENT_NOT_FOUND", e.getMessage(), 404));
        }
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<String> getByBarcode(@PathVariable String barcode) {
        try {
            String result = ingredientService.getProductByBarcode(barcode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("바코드 조회 실패: " + e.getMessage());
        }
    }
}
