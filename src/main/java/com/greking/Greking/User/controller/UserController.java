package com.greking.Greking.User.controller;

import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            User registeredUser = userService.registerUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "user regustered successfully");
            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //login api
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest){
        try{
            String userid = loginRequest.get("userId");
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

            //로그인 로직 처리 및 JWT토큰 생성
            String token = userService.login(userid, email, password);

            Map<String, String> response = new HashMap<>();
            response.put("token", token); //JWT 토큰 반환

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //회원 정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "userId") String userId) {
        try {
            userService.deleteUser(userId);
            String log = "회원 삭제기 완료되었습니다.";
            return new ResponseEntity<>(log, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //회원 정보 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(name = "userId") String userId){
        try{
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //닉네임 중복확인
    @GetMapping("/validate/{nickname}")
    public ResponseEntity<Boolean> validateNickname(@PathVariable(name = "nickname")String nickname){
        return ResponseEntity.ok(userService.validateNickname(nickname));
    }


    //유저 코스 등록하기
    @PostMapping("/{userId}/my-courses/{courseId}")
    public ResponseEntity<?> addCourseToMyCourses(@PathVariable (name = "userId") String userId, @PathVariable (name = "courseId") Long courseId) {
        try {
            UserCourse userCourse = userService.addCourseToMyCourse(userId, courseId);
            return new ResponseEntity<>(userCourse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //유저 코스 가져오기
    @GetMapping("/{userId}/my-courses")
    public ResponseEntity<?> getMyCourse(@PathVariable(name = "userId") String userId){
        try{
            List<UserCourse> userCourses = userService.getMyCourse(userId);
            return new ResponseEntity<>(userCourses, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //유저 코스 삭제하기
    @DeleteMapping("/{userId}/my-courses/{userCourseId}")
    public ResponseEntity<?> deleteSpecificCourse(@PathVariable(name = "userId") String userId, @PathVariable(name = "userCourseId") Long userCourseId) {
        try {
            userService.deleteCourseToMyCourse(userId, userCourseId);
            String log = "유저코스가 삭제가 완료되었습니다.";
            return new ResponseEntity<>(log,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //등산완료시 등산데이터 저장
    @PostMapping("/{userId}/my-courses/{userCourseId}/complete")
    public ResponseEntity<?> completeHiking(@PathVariable(name="userId") String userId, @PathVariable(name="userCourseId") Long userCourseId,
                                            @RequestBody Map<String, String> params){
        try{
            String distance = params.get("distance");
            String calories = params.get("calories");
            String duration = params.get("duration");
            String altitude = params.get("altitude");
            userService.completeHiking(userId,userCourseId,distance,calories,duration, altitude);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
