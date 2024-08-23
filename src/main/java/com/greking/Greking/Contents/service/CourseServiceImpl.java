package com.greking.Greking.Contents.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final ApiClient apiClient;

    private final MountainService mountainService;
    private final RestaurantService restaurantService;
    private final WeatherService weatherService;

    public CourseServiceImpl(CourseRepository courseRepository, ApiClient apiClient, @Lazy MountainService mountainService, RestaurantService restaurantService, WeatherService weatherService) {
        this.courseRepository = courseRepository;
        this.mountainService = mountainService;
        this.apiClient = apiClient;
        this.restaurantService = restaurantService;
        this.weatherService = weatherService;
    }

    @Override
    public CourseDto getCourseById(Long id) {
        logger.info("Fetching course by ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("course not found"));
        return convertToDto(course);
    }

    @Override
    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepository.findAll();
    }

    private CourseDto convertToDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .information(course.getInformation())
                .difficulty(course.getDifficulty())
                .distance(course.getDistance())
                .duration(course.getDuration())
                .altitude(course.getAltitude())
                .direction(course.getDirection())
                .build();
    }

    @Override
    @Transactional
    public void addAllCourses() throws JsonProcessingException {
        logger.info("Starting to add all courses");
        List<Mountain> allMountains = mountainService.getAllMountains();

        for (Mountain mountain : allMountains) {
            logger.info("Processing mountain: {}", mountain.getName());
            Course course = new Course();
            course.setMountain(mountain);
            saveCourse(course);
        }
        logger.info("Finished adding all courses");
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) throws JsonProcessingException {
        Mountain mountain = course.getMountain();
        String mountainName = mountain.getName();
        double latitude = mountain.getLatitude();
        double longitude = mountain.getLongitude();

        logger.info("Saving course for mountain: {} (lat: {}, lon: {})", mountainName, latitude, longitude);

        // 공공데이터 API에서 산 정보를 가져옴 => 다시작업
        String responseData = apiClient.fetchMountainData(mountainName, latitude, longitude);
        logger.debug("API response data: {}", responseData);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseData);

        JsonNode resultNode = root.path("response").path("result");

        String courseName = resultNode.path("courseName").asText();
        String difficulty = resultNode.path("difficulty").asText();
        int distance = resultNode.path("distance").asInt();

        logger.info("Parsed course data - Name: {}, Difficulty: {}, Distance: {}",
                courseName, difficulty, distance);

        course.setCourseName(courseName);
        course.setDifficulty(difficulty);
        course.setDistance(distance);
        course.setMountainName(mountainName);

        // 2번째 정보 가져오기
        String directionData = apiClient.fetchInfoDirectionData(mountainName);
        logger.debug("Direction API response data: {}", directionData);

        JsonNode directionRoot = objectMapper.readTree(directionData);
        String direction = directionRoot.path("response").path("body").path("items").path("item").path("pbtrninfodscrt").asText();
        String information = directionRoot.path("response").path("body").path("items").path("item").path("mntninfodscrt").asText();

        logger.info("Parsed direction data - Direction: {}, Information: {}", direction, information);

        course.setDirection(direction);
        course.setInformation(information);

        // Course를 저장
        Course savedCourse = courseRepository.save(course);
        logger.info("Course saved with ID: {}", savedCourse.getId());

        return savedCourse;
    }

    @Override
    @Transactional
    public Course updateCourse(Long id) throws JsonProcessingException {
        logger.info("Updating course with ID: {}", id);

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("course not found"));

        List<Weather> updatedWeathers = weatherService.getWeatherForCourse(existingCourse);
        existingCourse.setWeathers(updatedWeathers);

        List<Restaurant> updatedRestaurants = restaurantService.getRestaurantForCourse(existingCourse);
        existingCourse.setRestaurants(updatedRestaurants);

        Course updatedCourse = courseRepository.save(existingCourse);
        logger.info("Course updated with new weather and restaurant data");

        return updatedCourse;
    }

    @Override
    public void deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);

        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            courseRepository.deleteById(id);
            logger.info("Course deleted");
        } else {
            throw new IllegalArgumentException("course not found");
        }
    }
}
