package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;


    //생성자주입
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/getInfo")
    public ResponseEntity<CourseDto> getCourseById(@RequestParam(name="courseId") Long courseId){
        try{
            CourseDto course = courseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e){
            System.out.println("Error occurred while fetching course: " + e.getMessage());
            return ResponseEntity.status(404).body(null); // 예외가 발생하면 404 응답
        }
    }

    //산에 따른 모든 코스정보 가져오기
    @GetMapping("/getCourse")
    public ResponseEntity<CourseDto> getCourseByMountain(@RequestParam(name="mountainName") String mountainName){
        try{
            CourseDto course = courseService.getCourseByMountain(mountainName);
            return ResponseEntity.ok(course);
        }   catch (Exception e){
            System.out.println("Error occurred while fetching course: " + e.getMessage());
            return ResponseEntity.status(404).body(null); // 예외가 발생하면 404 응답
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        try{
            List<CourseDto> courses = courseService.getAllCourses();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e){
            System.out.println("Error occurred while fetching courses: " + e.getMessage());
            return ResponseEntity.status(404).body(null); // 예외가 발생하면 404 응답
        }
    }

    //코스정보 저장, 수정, 삭제
    //비공개 api

    //데이터 intializer (모든 코스정보 업데이트)
    @PostMapping("/addAll")
    public ResponseEntity<?> addAllCourses(){
        try{
            courseService.addAllCourses();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        try{
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
