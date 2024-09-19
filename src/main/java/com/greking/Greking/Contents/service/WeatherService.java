package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.dto.WeatherDto;
import org.json.JSONException;

import java.util.List;

public interface WeatherService {

    // 기상청 API로부터 날씨 정보를 가져와 DB에 저장하는 메서드
    void saveWeatherData(Long mountainId) throws JSONException;

    // 클라이언트 측에서 요청 시 DB에서 날씨 정보를 조회하는 메서드
    WeatherDto getWeatherForecast(String mountainName);

    //db에 있는 모든 산에 대해 날씨정보 생성하는 메서드
    void saveAllWeatherData();

    //db에 있는 모든 산에 대해 날씨정보 추가하는 메서드
    void updateAllWeatherData() throws JSONException;
}
