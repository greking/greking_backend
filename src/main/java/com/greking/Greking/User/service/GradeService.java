package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.Grade;

public interface GradeService {

    //경험치 추가 메서드
    boolean addExperience(Grade grade, String difficulty);

}
