package com.greking.Greking.Review.service;

import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.dto.ReviewDto;


public interface ReviewService {


    //리뷰 가져오기
    //리뷰 등록

    ReviewDto getReview(Long userId, Long userCourseId);

    Review createReview(Long userId, Long userCorseId, int review_score, String review_difficulty, String review_text);


}
