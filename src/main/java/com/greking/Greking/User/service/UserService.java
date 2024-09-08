package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;

import java.util.List;

public interface UserService {

    //회원 정보 가져오기
    User getUserById(String userId);

    //회원 등록
    User registerUser(User user) throws Exception;

    //회원 로그인
    String login(String userid, String email, String Password);

    //회원삭제 method
    void deleteUser(String userId);

    //닉네임 중복 method
    boolean validateNickname(String nickname);


    //회원 코스정보 가져오기
    List<UserCourse> getMyCourse(String userId);

    //회원 코스정보 담기
    UserCourse addCourseToMyCourse(String userId, Long courseId);


    //회원 코스정보 삭제
    void deleteCourseToMyCourse(String userId, Long userCourseId);


    //등산코스 완료
    void completeHiking(String userId, Long userCourseId, String distance, String calories, String duration, String altitude);

    User getUserByEmail(String email);


    //회원 정보 수정 => 2.0 구현예정

}
