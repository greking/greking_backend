package com.greking.Greking.User.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.repository.CourseRepository;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import com.greking.Greking.User.dto.UserCourseDto;
import com.greking.Greking.User.repository.UserCourseRepository;
import com.greking.Greking.User.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GradeService gradeService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, UserCourseRepository userCourseRepository, CourseRepository courseRepository, BCryptPasswordEncoder passwordEncoder, GradeService gradeService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.gradeService = gradeService;
    }



    @Override
    @Transactional
    public User registerUser(User user) throws Exception {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("User {} registered", user);
        return userRepository.save(user);
    }

    // 회원정보 가져오기
    @Override
    public User getUserById(String userId) {
        return userRepository.findByUserid(userId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 유저를 찾을 수 없습니다."));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 email의 유저를 찾을 수 없습니다."));
    }


    // 로그인로직 구현
    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // JWT 토큰 생성 및 반환 (JWT 토큰 로직 추가 필요)
        return jwtTokenProvider.createToken(user.getUserid(), user.getEmail());
    }


    // 회원 삭제
    @Override
    @Transactional
    public void deleteUser(String userId) {
        Optional<User> userOptional = userRepository.findByUserid(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteByUserid(userId); // 사용자 삭제
            logger.info("User with ID {} deleted", userId);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    // 회원 유효성 검증
    private void validateUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists.");
        }
        if (!user.isTermsOfServiceAccepted() || !user.isPrivacyPolicyAccepted()) {
            throw new Exception("Terms of Service and Privacy Policy must be accepted.");
        }
    }

    // 닉네임 중복 검사
    @Override
    public boolean validateNickname(String nickname) {
        logger.info("Validating user nickname {}", nickname);
        return userRepository.existsByNickname(nickname);
    }

    // 회원 코스 - 예정가져오기
    @Override
    public List<UserCourseDto> getMyExpectedCourse(String userId) {
        User user = getUserById(userId);
        List<UserCourse> userCourses = userCourseRepository.findByUser(user);

        //stream api활용해서 특정값만 가져오기
        return userCourses.stream()
                .map(this::convertToDto)
                .filter(userCourse -> "예정".equals(userCourse.getStatus()))
                .collect(Collectors.toList());
    }

    // 회원 코스 - 완료가져오기
    @Override
    public List<UserCourseDto> getMyCompleteCourse(String userId) {
        User user = getUserById(userId);
        List<UserCourse> userCourses = userCourseRepository.findByUser(user);

        return userCourses.stream()
                .map(this::convertToDto)
                .filter(userCourse -> "완료".equals(userCourse.getStatus()))
                .collect(Collectors.toList());

    }


    private UserCourseDto convertToDto(UserCourse userCourse) {
        return UserCourseDto.builder()
                .userCourseId(userCourse.getUserCourseId())
                .course(userCourse.getCourse())
                .difficulty(userCourse.getDifficulty())
                .addedTime(userCourse.getAddedTime())
                .distance(userCourse.getDistance())
                .calories(userCourse.getCalories())
                .duration(userCourse.getDuration())
                .courseName(userCourse.getCourseName())
                .altitude(userCourse.getAltitude())
                .status(userCourse.getStatus())
                .build();
    }


    // 회원 코스 추가
    @Override
    @Transactional
    public UserCourse addCourseToMyCourse(String userId, String courseName) {
        User user = getUserById(userId);
        Course course = courseRepository.findByCourseName(courseName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        UserCourse userCourse = new UserCourse();
        userCourse.setCourseName(course.getCourseName());
        userCourse.setDifficulty(course.getDifficulty());
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setAddedTime(LocalDateTime.now().format(formatter));

        return userCourseRepository.save(userCourse);
    }

    // 회원 코스 삭제
    @Override
    @Transactional
    public void deleteCourseToMyCourse(String userId, Long userCourseId) {
        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse not found"));

        // 유저의 ID가 일치하는지 확인
        if (!userCourse.getUser().getUserid().equals(userId)) {
            throw new IllegalArgumentException("UserCourse does not belong to the specified user");
        }

        userCourseRepository.delete(userCourse);
    }

    // 등산 완료
    @Override
    public boolean completeHiking(String userId, Long userCourseId, String distance, String calories, String duration, String altitude) {
        User user = getUserById(userId);
        UserCourse userCourse = userCourseRepository.findById(userCourseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCourse not found"));

        // 유저의 ID가 일치하는지 확인
        if (!userCourse.getUser().getUserid().equals(userId)) {
            throw new IllegalArgumentException("UserCourse does not belong to the specified user");
        }

        // 코스 난이도에 따른 레벨업
        boolean leveledUp = gradeService.addExperience(user.getGrade(), userCourse.getDifficulty());


        userCourse.setStatus("완료");
        userCourse.setAltitude(altitude);
        userCourse.setDistance(distance);
        userCourse.setCalories(calories);
        userCourse.setDuration(duration);

        userCourseRepository.save(userCourse);

        return leveledUp;
    }
}

