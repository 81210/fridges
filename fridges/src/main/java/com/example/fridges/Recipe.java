package com.example.fridges;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    private String rcpSeq;       // 레시피 번호
    private String rcpNm;        // 레시피 이름
    private String rcpPat2;      // 카테고리 (반찬, 국 등)
    private String rcpWay2;      // 조리방법 (찌기, 볶기 등)

    @Column(length = 1000)
    private String rcpPartsDtls; // 재료

    @Column(length = 2000)
    private String manual01;     // 조리순서1
    @Column(length = 2000)
    private String manual02;
    @Column(length = 2000)
    private String manual03;
    @Column(length = 2000)
    private String manual04;
    @Column(length = 2000)
    private String manual05;
    @Column(length = 2000)
    private String manual06;

    private String attFileNoMain; // 대표 이미지
    private String hashTag;       // 해시태그
    private String infoEng;       // 칼로리
    private String infoCar;       // 탄수화물
    private String infoPro;       // 단백질
    private String infoFat;       // 지방
    private String infoNa;        // 나트륨
}