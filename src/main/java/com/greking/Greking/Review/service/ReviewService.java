package com.greking.Greking.Review.service;

import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.dto.ReviewDto;

import java.util.List;


public interface ReviewService {


    //리뷰 가져오기
    //리뷰 등록

    //전체 리뷰가져오기
    List<ReviewDto> getAllReview();

    //코스별 리뷰가져오기
    List<ReviewDto> getReviewWithCourse(String courseName);

    ReviewDto getReview(String userId, Long userCourseId);

    Review createReview(String userId, Long userCourseId, int review_score, String review_difficulty, String review_text);


}
