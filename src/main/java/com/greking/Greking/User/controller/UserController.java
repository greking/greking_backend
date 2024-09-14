package com.greking.Greking.User.controller;

import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.repository.UserRepository;
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


    private UserService userService;

    private UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

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

            User user = userRepository.findByUserid(userid)
                    .orElseThrow(() -> new RuntimeException("user is not found"));

            //로그인 로직 처리 및 JWT토큰 생성
            String token = userService.login(userid, email, password);

            Map<String, String> response = new HashMap<>();
            response.put("nickname", user.getNickname()); //nickname반환
            response.put("token", token); //JWT 토큰 반환

            System.out.println(user.getNickname() + user.getEmail());

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
    @PostMapping("/{userId}/my-courses/{courseName}")
    public ResponseEntity<?> addCourseToMyCourses(@PathVariable (name = "userId") String userId, @PathVariable (name = "courseName") String courseName) {
        try {
            UserCourse userCourse = userService.addCourseToMyCourse(userId, courseName);

            return new ResponseEntity<>(userCourse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //유저 코스 - 예정 가져오기
    @GetMapping("/{userId}/my-courses/expected")
    public ResponseEntity<?> getMyExpectedCourse(@PathVariable(name = "userId") String userId){
        try{
            List<UserCourse> userCourses = userService.getMyExpectedCourse(userId);
            return new ResponseEntity<>(userCourses, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //유저코스 - 완료 가져오기
    @GetMapping("/{userId}/my-courses/complete")
    public ResponseEntity<?> getMyCompleteCourse(@PathVariable(name = "userId") String userId){
        try{
            List<UserCourse> userCourses = userService.getMyCompleteCourse(userId);
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
