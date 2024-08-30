package com.greking.Greking.Common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.service.CourseService;
import com.greking.Greking.Contents.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private WeatherService weatherService;

    public ScheduledTasks(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @Scheduled(cron = "0 0 7 * *?")
    public void updateWeatherData() throws JsonProcessingException{
        weatherService.saveAllWeatherData();
    }
}
