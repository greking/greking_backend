package com.greking.Greking.User.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.repository.PasswordResetTokenRepository;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;

    private final GradeService gradeService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserCourseRepository userCourseRepository, CourseRepository courseRepository, BCryptPasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository, GradeService gradeService) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.gradeService = gradeService;
    }



    @Override
    @Transactional
    public User registerUser(User user) throws Exception {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    //회원정보 가져오기
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 유저를 찾을 수 없습니다."));
    }


    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            tokenRepository.deleteByUser(user); // 관련된 비밀번호 재설정 토큰 삭제
            userRepository.deleteById(userId); // 사용자 삭제
            System.out.println("User with ID " + userId + " deleted.");
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }


    //회원 있는지 유효성 검증
    private void validateUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists.");
        }
        if (!user.isTermsOfServiceAccepted() || !user.isPrivacyPolicyAccepted()) {
            throw new Exception("Terms of Service and Privacy Policy must be accepted.");
        }
    }

    //회원 코스 찾기
    @Override
    public List<UserCourse> getMyCourse(Long userId) {
        User user = getUserById(userId);
        System.out.println(userCourseRepository.findByUser(user));
        return userCourseRepository.findByUser(user);
    }

    //회원 코스 담기
    @Override
    @Transactional
    public UserCourse addCourseToMyCourse(Long userId, Long courseId) {
        User user = getUserById(userId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setAddedAt(LocalDateTime.now());
        return userCourseRepository.save(userCourse);
    }


    //회원 코스 삭제
    @Override
    @Transactional
    public void deleteCourseToMyCourse(Long userId, Long userCourseId) {
        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse not found"));

        // 유저의 ID가 일치하는지 확인
        if (!userCourse.getUser().getUserid().equals(userId)) {
            throw new IllegalArgumentException("UserCourse does not belong to the specified user");
        }

        userCourseRepository.delete(userCourse);
    }

    //등산 완료
    @Override
    public void completeHiking(Long userId, Long userCourseId, double distance, double calories, long duration, double altitude) {

        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse not found"));

        // 유저의 ID가 일치하는지 확인
        if (!userCourse.getUser().getUserid().equals(userId)) {
            throw new IllegalArgumentException("UserCourse does not belong to the specified user");
        }

        userCourse.setStatus("완료"); //status를 "완료"로 업데이트
        userCourse.setAltitude(altitude);
        userCourse.setDistance(distance);
        userCourse.setCalories(calories);
        userCourse.setDuration(duration);

        userCourseRepository.save(userCourse);
    }
}
