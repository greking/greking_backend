package com.greking.Greking.Review.service;

import com.greking.Greking.Review.domain.Review;


public interface ReviewService {


    //리뷰 가져오기
    //리뷰 등록

    Review getReview(Long userId, Long userCourseId);

    Review createReview(Long userId, Long userCorseId, int review_score, String review_difficulty, String review_text);


}
