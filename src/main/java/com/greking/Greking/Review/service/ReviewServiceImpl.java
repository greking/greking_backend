package com.greking.Greking.Review.service;

import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.dto.ReviewDto;
import com.greking.Greking.Review.repository.ReviewRepository;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import com.greking.Greking.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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



    //리뷰 전체 가져오기
    @Override
    public List<ReviewDto> getAllReview() {
        List<Review> review = reviewRepository.findAll();
        return review.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ReviewDto> getReviewWithCourse(Long courseId) {
        List<Review> review = reviewRepository.findByCourseCourseId(courseId);
        return review.stream() //리스트 'review'에서 스트림을 생성
                .map(this::convertToDto) //각 review 객체를 reviewDto로 변환
                .collect(Collectors.toList()); //
    }

    //리뷰조회
    //사용자 찾기 -> 사용자의 유저코스 찾기 -> 사용자의 유저코스의 작성된 리뷰 찾기
    @Override
    @Transactional
    public ReviewDto getReview(String userId, Long userCourseId) {
        User user = userService.getUserById(userId);
        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 코스를 찾을 수 없습니다."));
        Review review = reviewRepository.findByUserAndUserCourse(user, userCourse)
                .orElseThrow(() -> new RuntimeException("해당 ID의 리뷰를 찾을 수 없습니다."));

        return convertToDto(review);
    }

    //리뷰생성
    @Override
    @Transactional
    public Review createReview(String userId, Long userCourseId,
                               int review_score, String review_difficulty, String review_text) {

        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new RuntimeException("UserCourse not found"));

        if (!"완료".equals(userCourse.getStatus())) {
            throw new IllegalStateException("리뷰는 등산 코스가 완료된 후에만 작성 가능합니다.");
        }

        Review review = new Review();

        review.setNickName(user.getNickname());
        review.setCourseName(userCourse.getCourseName());
        review.setCourse(userCourse.getCourse());
        review.setUser(userService.getUserById(userId));
        review.setUserCourse(userCourse);
        review.setReview_score(review_score);
        review.setReview_difficulty(review_difficulty);
        review.setReview_text(review_text);


        return reviewRepository.save(review);
    }

    private ReviewDto convertToDto(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .review_score(review.getReview_score())
                .review_difficulty(review.getReview_difficulty())
                .review_text(review.getReview_text())
                .courseName(review.getCourseName())
                .nickName(review.getNickName())
                .build();
    }

}
