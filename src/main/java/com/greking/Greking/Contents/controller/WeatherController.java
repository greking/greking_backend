package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;


    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 산의 주소를 기반으로 날씨 정보를 가져오는 엔드포인트
     * @param mountainAddress 산의 주소 (addressState)
     * @return 해당 산의 날씨 정보
     */
    @GetMapping("/forecast")
    public Weather getWeatherForecast(@RequestParam String mountainAddress) {
        return weatherService.getWeatherForecast(mountainAddress);
    }

}
