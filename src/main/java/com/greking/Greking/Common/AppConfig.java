package com.greking.Greking.Common;

import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.service.*;
import com.greking.Greking.Survey.service.SurveyService;
import com.greking.Greking.Survey.service.SurveyServiceImpl;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.service.EmailService;
import com.greking.Greking.User.service.UserService;
import com.greking.Greking.User.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Bean
    public SurveyService surveyService(UserRepository userRepository){
        return new SurveyServiceImpl(userRepository);
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(new RestTemplate());
    }
}
