package com.greking.Greking.Common;

import com.greking.Greking.Survey.service.SurveyService;
import com.greking.Greking.Survey.service.SurveyServiceImpl;
import com.greking.Greking.User.repository.UserRepository;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.service.EmailService;
import com.greking.Greking.User.service.UserService;
import com.greking.Greking.User.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                                   EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        return new UserServiceImpl(userRepository, passwordEncoder, emailService, tokenRepository);
    }

    @Bean
    public SurveyService surveyService(UserRepository userRepository){
        return new SurveyServiceImpl(userRepository);
    }
}
