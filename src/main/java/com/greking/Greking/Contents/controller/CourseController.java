package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.service.CourseService;
import com.greking.Greking.Contents.service.MountainService;
import com.greking.Greking.Contents.service.RestaurantService;
import com.greking.Greking.Contents.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;
    private final MountainService mountainService;
    private RestaurantService restaurantService;
    private WeatherService weatherService;

    @Autowired
    public CourseController(CourseService courseService, MountainService mountainService, RestaurantService restaurantService, WeatherService weatherService) {

        this.courseService = courseService;
        this.mountainService = mountainService;
        this.restaurantService = restaurantService;
        this.weatherService = weatherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        try{
            courseService.getCourseById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses(){
        try{
            List<Course> courses = courseService.getAllCourses();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //코스정보 저장, 수정, 삭제
    //비공개 api
    @PostMapping
    public ResponseEntity<?> saveCourse(@RequestBody Course course){
        try{
            Course createdCourse = courseService.saveCourse(course);
            return new ResponseEntity<>(createdCourse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id){
        try{
            Course updatedCourse = courseService.updateCourse(id);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        try{
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
