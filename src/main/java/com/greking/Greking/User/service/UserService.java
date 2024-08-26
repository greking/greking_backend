package com.greking.Greking.User.service;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;

public interface UserService {

    //회원 등록
    User registerUser(User user) throws Exception;

    //회원 정보 수정


    //회원삭제 method
    void deleteUser(Long userId);

}
