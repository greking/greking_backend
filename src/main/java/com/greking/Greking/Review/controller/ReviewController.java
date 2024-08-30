package com.greking.Greking.Review.controller;


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


    //리뷰 저장
    @PostMapping("/{userId}/{userCourseId}")
    public ResponseEntity<?> createReview(@PathVariable Long userId, @PathVariable Long userCourseId,
                                          @RequestBody Map<String, Object> params){
        try{
            int review_score = (Integer) params.get("review_score");
            String review_difficulty = (String) params.get("review_difficulty");
            String review_text = (String) params.get("review_text");
            reviewService.createReview(userId, userCourseId, review_score,review_difficulty,review_text);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //리뷰 조회
    @GetMapping("/{userId}/{userCourseId}")
    public ResponseEntity<?> getReview(@PathVariable Long userId, @PathVariable Long userCourseId){
        try{
            reviewService.getReview(userId, userCourseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 상태가 완료가 아닐 때
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
