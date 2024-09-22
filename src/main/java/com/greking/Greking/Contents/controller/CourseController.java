package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.dto.DirectoryRequest;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Contents.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;
    private final CourseRepository courseRepository;

    //생성자주입
    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }


    // 로컬 이미지 경로를 저장하고 DB에 저장하는 API
    @PostMapping("/save-image-path")
    public ResponseEntity<String> saveCourseImagePath(@RequestBody String courseName, @RequestBody String imagePath) {
        try {
            // 이미지 경로를 DB에 저장
            courseService.saveImagePath(courseName, imagePath);
            return ResponseEntity.ok("Image path saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image path.");
        }
    }

    @GetMapping("/image/{courseName}")
    public ResponseEntity<Resource> getCourseImage(@PathVariable(name="courseName") String courseName) {
        // 코스 정보에서 이미지 경로를 가져옴
        Course course = courseRepository.findByCourseName(courseName);

        System.out.println(course);

        if (course != null) {
            try {
                // 이미지 파일 경로에서 Resource 객체 생성
                Path imagePath = Paths.get(course.getCourseImage());
                Resource imageResource = new UrlResource(imagePath.toUri());

                // 이미지 파일을 ResponseEntity로 반환
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagePath.getFileName().toString() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // 적절한 MIME 타입으로 설정 (여기서는 JPEG로 가정)
                        .body(imageResource);

            } catch (MalformedURLException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
    @GetMapping("/getCourse/{mountainName}")
    public ResponseEntity<Map<String, Object>> getCourseByMountain(@PathVariable(name="mountainName") String mountainName){
        try{
            Map<String, Object> course = courseService.getCourseByMountain(mountainName);
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
