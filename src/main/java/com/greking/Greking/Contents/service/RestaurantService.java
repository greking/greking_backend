package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Restaurant;

import java.util.List;

public interface RestaurantService {

    //식당정보 api로부터 가져오기
    //식당정보 코스정보로 전달하기

    public List<Restaurant> fetchRestaurantDataForCourse(Course course);
    public List<Restaurant> getRestaurantForCourse(Course course);
}
