package com.greking.Greking.User.controller;

import com.greking.Greking.User.domain.PasswordResetToken;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.service.PasswordResetService;
import com.greking.Greking.User.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;
    private PasswordResetService passwordResetService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/password-reset-request")
    public ResponseEntity<?> passwordResetRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            passwordResetService.createPasswordResetToken(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean isValid = passwordResetService.validatePasswordResetToken(token);
        if (isValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/password-reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            passwordResetService.resetPassword(token, newPassword);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //회원 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //회원 정보 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@RequestParam(name = "userId") Long userId){
        try{
            userService.getUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //유저 코스 가져오기
    @GetMapping("/{userId}/my-course")
    public ResponseEntity<?> getMyCourse(@RequestParam(name = "userId") Long userId){
        try{
            userService.getMyCourse(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //유저 코스 등록하기
    @PostMapping("/{userId}/my-courses/{courseId}")
    public ResponseEntity<?> addCourseToMyCourses(@RequestParam (name = "userId") Long userId, @RequestParam (name = "courseId") Long courseId) {
        try {
            userService.addCourseToMyCourse(userId, courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //유저 코스 삭제하기
    @DeleteMapping("/my-courses/{userCourseId}")
    public ResponseEntity<?> deleteSpecificCourse(@RequestParam(name = "courseId") Long userCourseId) {
        try {
            userService.deleteCourseToMyCourse(userCourseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //등산완료시 등산데이터 저장
    @PostMapping("/{userId}/my-course/{courseId}/complete")
    public ResponseEntity<?> completeHiking(@RequestParam(name="userId") Long userId, @RequestParam(name="courseId") Long courseId,
                                            @RequestParam double distance, @RequestParam double calories,
                                            @RequestParam long duration){
        try{
            userService.completeHiking(userId,courseId,distance,calories,duration);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
