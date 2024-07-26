package com.greking.Greking.service;

import com.greking.Greking.domain.User;
import com.greking.Greking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserService {

//    @Autowired
    private UserRepository userRepository;

//    @Transactional
//    public User registerUser(User user){
////        validateUser(user);
//        return userRepository.save(user);
//    }

    private void validateUser(User user){

    }


    @Transactional
    public void increaseExperience(User user, int experience){

    }

}
