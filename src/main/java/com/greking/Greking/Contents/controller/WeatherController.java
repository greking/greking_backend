package com.greking.Greking.Contents.controller;


import com.greking.Greking.Contents.dto.WeatherDto;
import com.greking.Greking.Contents.service.WeatherService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/getInfo")
    public ResponseEntity<WeatherDto> getWeatherForecast(@RequestParam(name = "mountainId") Long mountainId) {
        try {
            WeatherDto weather = weatherService.getWeatherForecast(mountainId);
            return ResponseEntity.ok(weather); // 데이터가 있을 경우 200 응답과 함께 데이터 반환
        } catch (Exception e) {
            System.out.println("Error occurred while fetching weather: " + e.getMessage());
            return ResponseEntity.status(404).body(null); // 예외가 발생하면 404 응답
        }
    }

    @PostMapping("/fetch/{mountainId}")
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

    @PatchMapping("/update")
    public ResponseEntity<String> updateWeatherData() {
        try {
            weatherService.updateAllWeatherData();
            return ResponseEntity.ok("Weather data for all mountains successfully fetched and updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch and update weather data for all mountains: " + e.getMessage());
        }
    }

}
