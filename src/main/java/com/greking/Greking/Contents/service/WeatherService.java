package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Weather;

import java.util.List;

public interface WeatherService {

    //날씨 정보 api로부터 가져오기
    //날씨 정보 코스정보에게 전달하기
    //날씨 정보 저장하기

    public List<Weather> fetchWeatherDataForCourse(Course course);
    public List<Weather> getWeatherForCourse(Course course);
}
