package com.example.fridges.service;

import com.example.fridges.entity.Ingredient;
import com.example.fridges.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 식재료를 찾을 수 없습니다"));
    }

    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(Long id, Ingredient updated) {
        Ingredient ingredient = getById(id);
        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setUnit(updated.getUnit());
        ingredient.setExpiryDate(updated.getExpiryDate());
        ingredient.setLocation(updated.getLocation());
        ingredient.setStatus(updated.getStatus());
        ingredient.setMemo(updated.getMemo());
        return ingredientRepository.save(ingredient);
    }

    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
    public Ingredient updateStatus(Long id, String status) {
        Ingredient ingredient = getById(id);
        ingredient.setStatus(status);
        return ingredientRepository.save(ingredient);
    }

}