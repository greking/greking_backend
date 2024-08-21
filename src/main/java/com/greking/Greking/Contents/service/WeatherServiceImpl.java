package com.greking.Greking.Contents.service;

import org.springframework.beans.factory.annotation.Autowired;



public class WeatherServiceImpl {

    private WeatherService weatherService;

    public WeatherServiceImpl(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
}
