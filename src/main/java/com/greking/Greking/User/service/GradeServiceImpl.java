package com.greking.Greking.User.service;


import com.greking.Greking.User.domain.Grade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeServiceImpl implements GradeService {


    @Override
    @Transactional
    public void addExperience(Grade grade, String difficulty){
        if (grade.getLevel() == 5){
            grade.setExperience(0);
            return ;
        }

        int experienceGained = Grade.DIFFICULTY_EXPERIENCE_MAP.getOrDefault(difficulty, 0);;
        grade.setExperience(grade.getExperience() + experienceGained);

        while (grade.getExperience() >= Grade.LEVEL_UP_THRESHOLD){
            levelUp(grade);
            if (grade.getLevel() == 5){
                grade.setExperience(0);
                break;
            }
        }
    }

    //레벨업 메서드
    private void levelUp(Grade grade){
        //최대 레벨 5로 가정
        if(grade.getLevel() < 5){
            grade.setLevel(grade.getLevel() + 1);
            grade.setExperience(grade.getExperience() - Grade.LEVEL_UP_THRESHOLD);
        }

        if (grade.getLevel() == 5){
            grade.setExperience(0);
        }

    }

}
