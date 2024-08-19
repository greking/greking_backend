package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;

public interface UserService {

    //회원 등록
    User registerUser(User user) throws Exception;

    //비밀번호 재설정 -> Email 찾기
    User findUserByEmail(String email);

    //비밀번호 재설정 토큰
//    void createPasswordResetTokenForUser(String email);

    //비밀번호 재설정 토큰 유효성 검증
    boolean validatePasswordResetToken(String token);

    //비밀번호 재설정 method
    void resetPassword(String token, String newPassword);

    //회원삭제 method
    void deleteUser(Long userId);

    //비밀번호 토큰 저장 테스트 메소드
    PasswordResetToken createPasswordResetToken(String email);
}
