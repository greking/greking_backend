package com.greking.Greking.Survey.service;

import com.greking.Greking.Survey.domain.SurveyResult;
import com.greking.Greking.Survey.domain.FitnessLevel;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;


@Service
public class SurveyServiceImpl implements SurveyService {

    private UserRepository userRepository;

    @Autowired
    public SurveyServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public int calculrateScore(SurveyResult result) {
        int totalScore = 0;
        try {
            for (Field field : SurveyResult.class.getDeclaredFields()) {
                if (field.getType() == int.class) {
                    field.setAccessible(true);
                    totalScore += field.getInt(result);
                    System.out.println(field.getInt(result));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace(); //예외발생시 예외 호출스택 콘솔 출력
        }
        return totalScore;
    }

    @Override
    @Transactional
    public FitnessLevel calculrateLevel(int value){
        if (value >= 1 && value <= 4) {
            return FitnessLevel.LEVEL1;
        } else if (value >= 5 && value <= 9) {
            return FitnessLevel.LEVEL2;
        } else if (value >= 10 && value <= 14) {
            return FitnessLevel.LEVEL3;
        } else if (value >= 15 && value <= 18) {
            return FitnessLevel.LEVEL4;
        } else {
            return FitnessLevel.LEVEL5;
        }
    }

    @Override
    @Transactional
    public void registerUserlevel(Long userId, SurveyResult result) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        int totalScore = calculrateScore(result);
        FitnessLevel fitnessLevel = calculrateLevel(totalScore);

        user.setFitnessLevel(fitnessLevel);
        System.out.println("Updated Fitness Level: " + user.getFitnessLevel());
        userRepository.save(user);
    }
}
