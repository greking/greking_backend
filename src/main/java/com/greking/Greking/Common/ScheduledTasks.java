package com.greking.Greking.Common;

import com.greking.Greking.Contents.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final WeatherService weatherService;

    public ScheduledTasks(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    @Scheduled(cron = "0 0 7 * *?")
    public void updateWeatherData(){
        try {
            weatherService.saveAllWeatherData();
            log.info("Weather data updated successfully at 7 AM.");
        } catch (Exception e) {
            log.error("Failed to update weather data at 7 AM: " + e.getMessage(), e);
            handleFailedExecution(e);
        }
    }

    private void handleFailedExecution(Exception e) {
        // 예외 발생 시 추가적으로 처리할 수 있는 로직 (예: 관리자에게 알림 전송 등)
        log.error("Handling failed execution of weather data update.", e);
        // 필요한 경우 추가적인 재시도나 예외 처리를 여기에 추가
    }
}
