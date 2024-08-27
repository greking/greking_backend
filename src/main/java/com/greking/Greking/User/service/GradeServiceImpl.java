package com.greking.Greking.User.service;


import com.greking.Greking.User.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl {

    private final UserRepository userRepository;

    public GradeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



}
