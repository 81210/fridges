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

    private String calculateStatus(LocalDate expiryDate) {   // status 계산
        if (expiryDate == null) return "여유";  // 유통기한 정보 없으면 여유로 간주
        long days = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (days < 0)  return "만료";  // 이미 지남
        if (days <= 3) return "임박"; // 3일이하
        if (days <= 7) return "주의"; // 7일이하
        return "여유"; // 그 이상 (8일 이상)    
    }

    public List<Ingredient> getAll() {  // 전체 조회 ( 홈화면에서 냉장고 전체 목록 볼때 )
        List<Ingredient> list = ingredientRepository.findAll();  // DB에서 전부 가져옴 findall
        list.forEach(i -> i.setStatus(calculateStatus(i.getExpiryDate())));  // 가져온거 전부다 status 계산
        return list;
    }

    public Ingredient getById(Long id) { // id로 조회

        Ingredient ingredient = ingredientRepository.findById(id) // id로 db에서 찾아옴 ( 특정 식재로 하나 클릭하고 상세정보 볼 때 )
                .orElseThrow(() -> new RuntimeException("해당 식재료를 찾을 수 없습니다")); // 못찾으면 던지고 
        ingredient.setStatus(calculateStatus(ingredient.getExpiryDate())); // 찾으면 ingredient에 담고 status 계산해서 넣어줌
        return ingredient; // controller한테 반환
    }

    public Ingredient create(Ingredient ingredient) { // 추가
        ingredient.setStatus(calculateStatus(ingredient.getExpiryDate())); // 저장전에 status 계산
        return ingredientRepository.save(ingredient); // DB에 저장
    }

    public Ingredient update(Long id, Ingredient updated) { // 수정
        Ingredient ingredient = getById(id); // -id로 기존 식재료 찾아옴
        ingredient.setName(updated.getName()); // 새 이름으로 업데이트
        ingredient.setQuantity(updated.getQuantity()); // 새 수량으로 업데이트
        ingredient.setUnit(updated.getUnit());// 새 단위로 업데이트 . . . 밑에 다 똑같음
        ingredient.setExpiryDate(updated.getExpiryDate());
        ingredient.setLocation(updated.getLocation());
        ingredient.setMemo(updated.getMemo());
        ingredient.setStatus(calculateStatus(updated.getExpiryDate())); // status 재계산
        return ingredientRepository.save(ingredient); // dㅍ에 저장
    }

    public void delete(Long id) { // 삭제
        ingredientRepository.deleteById(id);
    }

    public Ingredient updateStatus(Long id, String status) { // status 업데이트 ( 여유 임박 등 수동으로 바꿀 때 )
        Ingredient ingredient = getById(id);
        ingredient.setStatus(status);
        return ingredientRepository.save(ingredient);
    }

    public String getProductByBarcode(String barcode) throws Exception { // 바코드 API 호출
        String apiKey = "3174a244477848cb89b6";
        String serviceId = "C005";
        String url = "http://openapi.foodsafetykorea.go.kr/api/" + apiKey + "/" + serviceId + "/json/1/1/BAR_CD=" + barcode; 
        // 식품안전나라에서 제공하는 API URL, 바코드 정보로 제품명 가져옴
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(); // 이 URL로 연결
        conn.setRequestMethod("GET"); // 응답 받고 반환

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        return sb.toString();
    }
}