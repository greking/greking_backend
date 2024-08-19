package com.greking.Greking.Survey.service;


import com.greking.Greking.Survey.domain.SurveyResult;
import com.greking.Greking.Survey.domain.FitnessLevel;

public interface SurveyService {


    //설문조사 답 전부 더하는 method
    int calculrateScore(SurveyResult result);

    //설문조사를 통한 피트니스 레벨 지정
    FitnessLevel calculrateLevel(int value);


    //
    void registerUserlevel(Long userid, SurveyResult result);
}
