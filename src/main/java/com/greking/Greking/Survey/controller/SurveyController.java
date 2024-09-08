package com.greking.Greking.Survey.controller;

import com.greking.Greking.Survey.domain.SurveyRequest;
import com.greking.Greking.Survey.domain.SurveyResult;
import com.greking.Greking.Survey.service.SurveyServiceImpl;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    private final SurveyServiceImpl surveyService;
    @Autowired
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);


    @Autowired
    public SurveyController(SurveyServiceImpl surveyService, UserRepository userRepository){
        this.surveyService = surveyService;
        this.userRepository = userRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> registerLevel(@Valid @RequestBody SurveyRequest request) {
        try {
            String userId = request.getUserId();  // SurveyRequest에서 userId를 받아옴
            SurveyResult surveyResult = request.getSurveyResult();

            // userId를 사용해 User 객체를 조회
            logger.info("Attempting to find user with userId: {}", userId);
            User user = userRepository.findByUserid(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            logger.info("User found: {}", user.getUserid());

            // 실제 유저의 userId를 사용해 피트니스 레벨을 설정
            surveyService.registerUserlevel(user.getUserid(), surveyResult);
            logger.info("User level successfully registered for userId: {}", user.getUserid());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Runtime exception occurred: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
