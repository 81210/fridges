package com.example.fridges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트 앱임을 선언, 자동 설정 활성화
public class FridgesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FridgesApplication.class, args); // 서버 실행 시작점
	}

}
// Spring Boot 애플리케이션의 진입점 클래스, main 메서드에서 SpringApplication.run() 호출하여 앱 실행