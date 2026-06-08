package com.example.fridges.service;

import com.example.fridges.entity.Ingredient;
import com.example.fridges.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    // status 자동계산
    private String calculateStatus(LocalDate expiryDate) {
        if (expiryDate == null) return "여유";
        long days = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (days < 0)  return "만료";
        if (days <= 3) return "임박";
        if (days <= 7) return "주의";
        return "여유";
    }

    public List<Ingredient> getAll() {
        List<Ingredient> list = ingredientRepository.findAll();
        list.forEach(i -> i.setStatus(calculateStatus(i.getExpiryDate())));
        return list;
    }

    public Ingredient getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 식재료를 찾을 수 없습니다"));
        ingredient.setStatus(calculateStatus(ingredient.getExpiryDate()));
        return ingredient;
    }

    public Ingredient create(Ingredient ingredient) {
        ingredient.setStatus(calculateStatus(ingredient.getExpiryDate()));
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(Long id, Ingredient updated) {
        Ingredient ingredient = getById(id);
        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setUnit(updated.getUnit());
        ingredient.setExpiryDate(updated.getExpiryDate());
        ingredient.setLocation(updated.getLocation());
        ingredient.setMemo(updated.getMemo());
        ingredient.setStatus(calculateStatus(updated.getExpiryDate())); // 수동 status 무시하고 자동계산
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
