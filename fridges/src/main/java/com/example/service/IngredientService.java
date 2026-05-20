package com.example.fridges.service;

import com.example.fridges.entity.Ingredient;
import com.example.fridges.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.http.ResponseEntity;

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
    public String getProductByBarcode(String barcode) throws Exception {
    String apiKey = "3174a244477848cb89b6";
    String serviceId = "C005";
    String url = "http://openapi.foodsafetykorea.go.kr/api/" + apiKey + "/" + serviceId + "/json/1/1/BAR_CD=" + barcode;

    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    conn.setRequestMethod("GET");

    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) sb.append(line);
    br.close();

    return sb.toString();
}

}