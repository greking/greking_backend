package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Weather;

public interface WeatherService {

    // 기상청 API로부터 날씨 정보를 가져와 DB에 저장하는 메서드
    void fetchAndSaveWeatherData(String mountainAddress);

    // 클라이언트 측에서 요청 시 DB에서 날씨 정보를 조회하는 메서드
    Weather getWeatherForecast(String mountainAddress);
}
