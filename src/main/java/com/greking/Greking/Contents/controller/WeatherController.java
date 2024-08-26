package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.service.WeatherService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param mountainId 산의 id값
     * @return 해당 산의 날씨 정보
     */
    @GetMapping("/forecast")
    public ResponseEntity<Weather> getWeatherForecast(@RequestParam Long mountainId) {
        Weather weather = weatherService.getWeatherForecast(mountainId);
        return ResponseEntity.ok(weather);
    }

    @PostMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveWeatherData(@RequestParam Long mountainId) {
        try {
            weatherService.saveWeatherData(mountainId);
            return ResponseEntity.ok("Weather data successfully fetched and saved.");
        } catch (JSONException e) {
            return ResponseEntity.status(500).body("Failed to fetch and save weather data: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 전체 산의 날씨 정보를 업데이트하는 엔드포인트
    @PostMapping("/fetch-and-save-all")
    public ResponseEntity<String> fetchAndSaveAllWeatherData() {
        try {
            weatherService.saveAllWeatherData();
            return ResponseEntity.ok("Weather data for all mountains successfully fetched and saved.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch and save weather data for all mountains: " + e.getMessage());
        }
    }
}
