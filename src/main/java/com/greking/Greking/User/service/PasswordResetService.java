package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;

public interface PasswordResetService {

    //비밀번호 재설정 -> Email 찾기
    User findUserByEmail(String email);

    //비밀번호 재설정 토큰 => 구현중
//    void createPasswordResetTokenForUser(String email);

    //비밀번호 재설정 토큰 유효성 검증
    boolean validatePasswordResetToken(String token);

    //비밀번호 재설정 method
    void resetPassword(String token, String newPassword);

    //비밀번호 재설정 토큰 생성
    PasswordResetToken createPasswordResetToken(String email);
}
