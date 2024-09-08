package com.greking.Greking.Common;

import com.greking.Greking.Contents.service.*;
import com.greking.Greking.Survey.service.SurveyService;
import com.greking.Greking.Survey.service.SurveyServiceImpl;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
