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

    private String regId; //지역별 날씨상태, 강수확률가져오기
    private String regId2; //지역별 온도 가져오기

    private LocalDate forecastDate;
    private String condition; //날씨 상태 (맑음, 흐림)
    private float temparature;
    private int predictRain; //강수확률
    private LocalDateTime tmFc;
}
