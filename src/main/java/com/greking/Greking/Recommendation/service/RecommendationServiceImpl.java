package com.greking.Greking.Recommendation.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.dto.ReviewDto;
import com.greking.Greking.Review.repository.ReviewRepository;
import com.greking.Greking.Review.service.ReviewService;
import com.greking.Greking.Survey.domain.FitnessLevel;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService{


    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ReviewService reviewService;

    private final ReviewRepository reviewRepository;

    @Autowired
    public RecommendationServiceImpl(CourseRepository courseRepository, UserRepository userRepository, ReviewService reviewService, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }


    //cold start
    @Override
    public List<Course> recommendCoursesForUser(String userId) {
        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(user);

        List<Review> userReviews = reviewRepository.findByUser(user);

        List<ReviewDto> review = reviewService.getAllReview(); //전체 리뷰가져오기

        int reviewSize = review.size();


        // 사용자별 추천 로직 실행
        if (reviewSize >= 200){
            List<Course> favoriteCourses = findFavoriteCourses(userReviews);
            return recommendSimilarCourses(favoriteCourses, user);
        }

        //cold start
        else {
            String difficulty = mapFitnessLevelToDifficulty(user.getFitnessLevel());
            System.out.println(difficulty);
            return courseRepository.findByDifficulty(difficulty);
        }
    }

    //1. 사용자가 좋아한 코스 찾기 (평점 4점이상)
    private List<Course> findFavoriteCourses(List<Review> userReview){
        List<Course> favoriteCourses = new ArrayList<>();

        for (Review review : userReview) {
            if (review.getReview_score() >= 4) { // 예: 평점 4 이상인 코스만 고려
                favoriteCourses.add(review.getCourse());
            }
        }
        return favoriteCourses;
    }

    //2. 사용자가 좋아한 코스와 유사한 코스 찾기
    private List<Course> recommendSimilarCourses(List<Course> favoriteCourses, User user){
        List<Course> recommendedCourses = new ArrayList<>();

        for (Course favoriteCourse : favoriteCourses) {
            List<Course> similarCourses = findSimilarCourses(favoriteCourse, user);
            recommendedCourses.addAll(similarCourses);
        }
        return recommendedCourses;
    }

    //3. favorite Course 와 유사한 코스를 찾는 함수
    private List<Course> findSimilarCourses(Course favoriteCourse, User user) {
        List<Course> allCourses = courseRepository.findAll();
        List<Course> similarCourses = new ArrayList<>();

        // favoriteCourse와 비교할 사용자 숙련도와 피트니스 레벨을 벡터로 취급
        double[] favoriteCourseVector = createCourseVector(favoriteCourse, user);

        for (Course course : allCourses) {
            if (!course.equals(favoriteCourse)) {
                // 코스와 사용자의 피트니스 레벨, 난이도를 벡터로 취급하여 코사인 유사도를 계산
                double[] courseVector = createCourseVector(course, user);
                double similarity = CosineSimilarity.calculate(favoriteCourseVector, courseVector);

                // 유사도가 일정 기준 이상일 경우 추천 목록에 추가 (예: 유사도 0.7 이상)
                if (similarity > 0.7) {
                    similarCourses.add(course);
                }
            }
        }

        return similarCourses;
    }


    // 특정 코스의 벡터를 생성하는 함수 (난이도, 숙련도, 피트니스 레벨 등을 포함)
    private double[] createCourseVector(Course course, User user) {
        // 코스 난이도 및 피트니스 레벨을 벡터로 변환
        double difficulty = convertDifficultyToNumeric(course.getDifficulty());
        double fitnessLevel = convertFitnessLevelToNumeric(user.getFitnessLevel()); // 피트니스 레벨을 숫자로 변환
        double userExperienceLevel = user.getGrade().getLevel(); // 숙련도 레벨

        return new double[]{difficulty, fitnessLevel, userExperienceLevel};
    }

    // 난이도를 숫자로 변환하는 함수 (예: easy -> 1, normal -> 2, hard -> 3)
    private double convertDifficultyToNumeric(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 1.0;
            case "normal" -> 2.0;
            case "hard" -> 3.0;
            default -> 0.0;
        };
    }

    //값은 크게 의미없음
    private double convertFitnessLevelToNumeric(FitnessLevel fitnessLevel){
        return switch (fitnessLevel){
            case LEVEL1 -> 1.0;
            case LEVEL2 -> 2.0;
            case LEVEL3 -> 3.0;
            case LEVEL4 -> 4.0;
            case LEVEL5 -> 5.0;
            default -> throw new IllegalArgumentException("Invalid fitness level: " + fitnessLevel);
        };
    }



    private String mapFitnessLevelToDifficulty(FitnessLevel fitnessLevel){
        return switch (fitnessLevel) {
            case LEVEL1, LEVEL2 -> "easy";
            case LEVEL3 -> "normal";
            case LEVEL4, LEVEL5 -> "hard";
            default -> throw new IllegalArgumentException("Invalid fitness level: " + fitnessLevel);
        };
    }
}


