package com.greking.Greking.Review.service;


import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.repository.ReviewRepository;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import com.greking.Greking.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService, UserRepository userRepository, UserCourseRepository userCourseRepository) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
    }


    //리뷰조회
    //사용자 찾기 -> 사용자의 유저코스 찾기 -> 사용자의 유저코스의 작성된 리뷰 찾기
    @Override
    @Transactional
    public Review getReview(Long userId, Long userCourseId) {
        User user = userService.getUserById(userId);
        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 코스를 찾을 수 없습니다."));

        return reviewRepository.findByUserAndUserCourse(user, userCourse)
                .orElseThrow(() -> new RuntimeException("해당 ID의 리뷰를 찾을 수 없습니다."));
    }

    //리뷰생성
    @Override
    @Transactional
    public Review createReview(Long userId, Long userCourseId,
                               int review_score, String review_difficulty, String review_text) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new RuntimeException("UserCourse not found"));

        if (!"완료".equals(userCourse.getStatus())) {
            throw new IllegalStateException("리뷰는 등산 코스가 완료된 후에만 작성 가능합니다.");
        }

        Review review = reviewRepository.findByUserAndUserCourse(user,userCourse)
                .orElseThrow(() -> new RuntimeException("해당 ID의 리뷰를 찾을수 없습니다"));


        review.setNickName(user.getNickname());
        review.setCourseName(userCourse.getCourseName());
        review.setUser(userService.getUserById(userId));
        review.setUserCourse(userCourse);
        review.setReview_score(review_score);
        review.setReview_difficulty(review_difficulty);
        review.setReview_text(review_text);


        return reviewRepository.save(review);
    }


}
