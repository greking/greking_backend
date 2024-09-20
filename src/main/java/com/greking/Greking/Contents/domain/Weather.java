package com.greking.Greking.Contents.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weatherId;

    @ManyToOne
    @JoinColumn(name = "mountain_id") // 외래키 칼럼 이름 지정
    private Mountain mountain;

    @ManyToOne
    @JoinColumn(name = "course_id") // 외래키 칼럼 이름 지정
    private Course course;

    private String cityCode; // 지역별 온도 가져오기
    private String westEastCode; // 지역별 강수확률, 날짜, 날씨가져오기

    private LocalDateTime tmFc;

    // 산 이름과 대표 주소를 저장하기 위한 필드
    private String mountainName;
    private String addressState;

    //Today 정보
    private String forecastDate;
    private String condition;
    private float temperature;
    private int predictRain;

    //1일차 정보
    private String forecastDate1;
    private String condition1;
    private float temperature1;
    private int predictRain1;

    //2일차 정보
    private String forecastDate2;
    private String condition2;
    private float temperature2;
    private int predictRain2;


    // 3일차 정보
    private String forecastDate3;
    private String condition3; // 날씨 상태 (맑음, 흐림 등)
    private float temperature3;
    private int predictRain3; // 강수확률

    // 4일차 정보
    private String forecastDate4;
    private String condition4;
    private float temperature4;
    private int predictRain4;

    // 5일차 정보
    private String forecastDate5;
    private String condition5;
    private float temperature5;
    private int predictRain5;

    // 6일차 정보
    private String forecastDate6;
    private String condition6;
    private float temperature6;
    private int predictRain6;

    // 7일차 정보
    private String forecastDate7;
    private String condition7;
    private float temperature7;
    private int predictRain7;

}
