package com.greking.Greking.Recommendation.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Survey.domain.FitnessLevel;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService{


    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecommendationServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Course> recommendCoursesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String difficulty = mapFitnessLevelToDifficulty(user.getFitnessLevel());
        return courseRepository.findByDifficulty(difficulty);


        //next version -> review, category통해 collaborative 추천로직 진행
    }


    private String mapFitnessLevelToDifficulty(FitnessLevel fitnessLevel){
        switch (fitnessLevel){
            case LEVEL1:
            case LEVEL2:
                return "easy";
            case LEVEL3:
                return "normal";
            case LEVEL4:
            case LEVEL5:
                return "hard";
            default:
                throw new IllegalArgumentException("Invalid fitness level: " + fitnessLevel);
        }
    }
}
