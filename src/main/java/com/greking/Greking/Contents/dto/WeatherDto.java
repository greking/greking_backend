package com.greking.Greking.Contents.dto;

import com.greking.Greking.Contents.domain.Mountain;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherDto {

    private Long id;
    private Mountain mountain;

    private String westEastCode; //지역별 날씨상태, 강수확률가져오기
    private String cityCode; //지역별 온도 가져오기

    private String mountainName;
    private String addressState;

    private LocalDateTime tmFc;

    private LocalDate forecastDate3;
    private String condition3;
    private float temperature3;
    private int predictRain3;

    private LocalDate forecastDate4;
    private String condition4;
    private float temperature4;
    private int predictRain4;

    private LocalDate forecastDate5;
    private String condition5;
    private float temperature5;
    private int predictRain5;

    private LocalDate forecastDate6;
    private String condition6;
    private float temperature6;
    private int predictRain6;

    private LocalDate forecastDate7;
    private String condition7;
    private float temperature7;
    private int predictRain7;
}
