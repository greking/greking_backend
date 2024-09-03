package com.greking.Greking.Recommendation.controller;


import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Recommendation.service.RecommendationService;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Course>> recommendCourses(@PathVariable(name = "userId") Long userId){
        try{

            List<Course> recommendedCourses = recommendationService.recommendCoursesForUser(userId);
            return ResponseEntity.ok(recommendedCourses);
        } catch (Exception e){
            System.out.println("Error occurred while fetching review: " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }
}
