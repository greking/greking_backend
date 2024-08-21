package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Contents.repository.RestaurantRepository;
import com.greking.Greking.Contents.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService; //식당 업데이트 로직 필요

    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService; //날씨 업데이트 로직 필요


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, RestaurantRepository restaurantRepository, RestaurantService restaurantService, WeatherRepository weatherRepository, WeatherService weatherService) {
        this.courseRepository = courseRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
    }


    @Override
    public CourseDto getCourseById(Long id) {
        //ID로 조회
        Course course = courseRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("course not found"));

        return convertToDto(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    private CourseDto convertToDto(Course course){
        return CourseDto.builder()
                .id(course.getId())
                .coursename(course.getCoursename())
                .information(course.getInformation())
                .difficulty(course.getDifficulty())
                .duration(course.getDuration())
                .altitude(course.getAltitude())
                .direction(course.getDirection())
                .build();
    }

    @Override
    public Course saveCourse(Course course) {

        Course savecourse = courseRepository.save(course);

        //식당과 날씨 객체를 course와 연관 지어서 저장
        savecourse.getRestaurants().forEach(restaurant -> {
            restaurant.setCourse(savecourse);
            restaurantRepository.save(restaurant);
        });


        savecourse.getWeathers().forEach(weather -> {
            weather.setCourse(savecourse);
            weatherRepository.save(weather);
        });

        return savecourse;
    }


    @Override
    public Course updateCourse(Long id) {
        //기존 코스정보 가져오기
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("course not found"));


//        //코스정보 새로운 데이터 가져오기 (날씨, 식당)
//        List<Restaurant> updatedRestaurant = restaurantService.fetchRestaurantDataForCourse(existingCourse);
//        List<Weather> updatedWeather = weatherService.fetchWeatherDataForCoursr(existingCourse);
//
//        //기존 코스 정보에 업데이트 하기
//        existingCourse.setRestaurants(updatedRestaurant);
//        existingCourse.setWeathers(updatedWeather);
//
//        //관계 설정하기
//        updatedRestaurant.forEach(restaurant -> restaurant.setCourse(existingCourse));
//        updatedWeather.forEach(weather -> weather.setCourse(existingCourse));


//        return courseRepository.save(existingCourse);
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()){
            courseRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("course not found");
        }
    }


}
