package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Weather;
import org.json.JSONException;

public interface WeatherService {

    // 기상청 API로부터 날씨 정보를 가져와 DB에 저장하는 메서드
    void saveWeatherData(Long mountainId) throws JSONException;

    // 클라이언트 측에서 요청 시 DB에서 날씨 정보를 조회하는 메서드
    Weather getWeatherForecast(Long mountainId);

    //db에 있는 모든 산에 대해 날씨정보 업데이트하는 메서드
    void saveAllWeatherData();
}
