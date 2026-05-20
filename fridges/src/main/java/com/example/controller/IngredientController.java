package com.example.fridges.controller;

import com.example.fridges.entity.Ingredient;
import com.example.fridges.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAll() {
        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @PostMapping
    public Ingredient create(@RequestBody Ingredient ingredient) {
        return ingredientService.create(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }

    @PatchMapping("/{id}/status")
    public Ingredient updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ingredientService.updateStatus(id, status);
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<String> getByBarcode(@PathVariable String barcode) throws Exception {
        String result = ingredientService.getProductByBarcode(barcode);
        return ResponseEntity.ok(result);
    }
}