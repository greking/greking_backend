package com.greking.Greking.User.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;

import java.util.List;

public interface UserService {

    //회원 정보 가져오기
    User getUserById(Long userId);


    //회원 등록
    User registerUser(User user) throws Exception;

    //회원삭제 method
    void deleteUser(Long userId);


    //회원 코스정보 가져오기
    List<UserCourse> getMyCourse(Long userId);

    //회원 코스정보 담기
    UserCourse addCourseToMyCourse(Long userId, Long courseId);


    //회원 코스정보 삭제
    void deleteCourseToMyCourse(Long userId, Long userCourseId);


    //등산코스 완료
    void completeHiking(Long userId, Long userCourseId, double distance, double calories, long duration, double altitude);



    //회원 정보 수정 => 2.0 구현예정

}
