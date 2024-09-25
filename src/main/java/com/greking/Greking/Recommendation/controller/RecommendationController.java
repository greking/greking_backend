package com.greking.Greking.Recommendation.controller;


import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> recommendCourses(@PathVariable(name = "userId") String userId){
        try{
            List<Course> recommendedCourses = recommendationService.recommendCoursesForUser(userId);
            List<Map<String, String>> responseList = new ArrayList<>();

            int count = 0;
            // 각 코스의 mountainName과 courseName을 Map으로 변환하여 리스트에 추가
            for (Course course : recommendedCourses) {
                if (count == 5){
                    break;
                }
                Map<String, String> courseMap = new HashMap<>();
                courseMap.put("mountainName", course.getMountainName());
                courseMap.put("courseName", course.getCourseName());
                courseMap.put("courseImage", course.getCourseImage());
                responseList.add(courseMap);
                count++;

            }

            // 결과를 ResponseEntity로 반환
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while recommending courses");
        }
    }

}
