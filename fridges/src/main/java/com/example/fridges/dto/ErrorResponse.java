package com.example.fridges.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;  // 에러코드 ex "INGREDIENT_NOT_FOUND"
    private String message; // 에러 메세지 ex "해당 식재료를 찾을 수 없습니다."
    private int status; // HTTP 상태 코드 ex 404
}


// 에러 응답은 controller에서 예외가 발생했을 때 클라이언트에게 전달