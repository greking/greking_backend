package com.greking.Greking.Review.controller;


import com.greking.Greking.Review.dto.ReviewDto;
import com.greking.Greking.Review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    //리뷰 저장
    @PostMapping("/{userId}/{userCourseId}")
    public ResponseEntity<?> createReview(@PathVariable(name = "userId") Long userId, @PathVariable(name="userCourseId") Long userCourseId,
                                          @RequestBody Map<String, String> params){
        try{
            int review_score = Integer.parseInt(params.get("review_score"));
            String review_difficulty = params.get("review_difficulty");
            String review_text = params.get("review_text");
            reviewService.createReview(userId, userCourseId, review_score,review_difficulty,review_text);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //리뷰 조회
    @GetMapping("/{userId}/{userCourseId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable(name = "userId") Long userId, @PathVariable(name = "userCourseId") Long userCourseId){
        try{
            ReviewDto review = reviewService.getReview(userId, userCourseId);
            return ResponseEntity.ok(review);
        } catch (Exception e){
            System.out.println("Error occurred while fetching review: " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

}
