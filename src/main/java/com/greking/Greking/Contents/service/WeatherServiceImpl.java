package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{

    private WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    @Override
    public List<Weather> fetchWeatherDataForCourse(Course course) {
        return null;
    }

    @Override
    public List<Weather> getWeatherForCourse(Course course) {
        return null;
    }
}

