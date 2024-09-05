package com.greking.Greking.Contents.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {


    private final CourseRepository courseRepository;
    private final ApiClient apiClient;
    private final MountainService mountainService;


    public CourseServiceImpl(CourseRepository courseRepository, ApiClient apiClient, MountainService mountainService) {
        this.courseRepository = courseRepository;
        this.apiClient = apiClient;
        this.mountainService = mountainService;
    }

    @Override
    public CourseDto getCourseById(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 산을 찾을수 없습니다"));

        return convertToDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CourseDto convertToDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .information(course.getInformation())
                .course_info(course.getCourse_info())
                .courseImage(course.getCourseImage())
                .difficulty(course.getDifficulty())
                .distance(course.getDistance())
                .duration(course.getDuration())
                .altitude(course.getAltitude())
                .longitude(course.getLongitude())
                .latitude(course.getLatitude())
                .direction(course.getDirection())
                .build();
    }

    @Override
    @Transactional
    public void addAllCourses() throws JsonProcessingException {
        List<Mountain> allMountains = mountainService.getAllMountains();

        for (Mountain mountain : allMountains) {
            Course course = new Course();
            course.setMountain(mountain);
            saveCourse(course);
        }
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) throws JsonProcessingException {
        Mountain mountain = course.getMountain();
        String mountainName = mountain.getName();
        double latitude = mountain.getLatitude();
        double longitude = mountain.getLongitude();

        // 1번째 정보 가져오기
        String responseData = apiClient.fetchMountainData(mountainName, latitude, longitude);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseData);

        JsonNode resultNode = root.path("response").path("result");

        String courseName = resultNode.path("courseName").asText();
        String difficulty = resultNode.path("difficulty").asText();
        String distance = resultNode.path("distance").asText();


        course.setCourseName(courseName);
        course.setDifficulty(difficulty);
        course.setDistance(distance);
        course.setMountainName(mountainName);

        // 2번째 정보 가져오기
        String directionData = apiClient.fetchInfoDirectionData(mountainName);

        JsonNode directionRoot = objectMapper.readTree(directionData);
        String direction = directionRoot.path("response").path("body").path("items").path("item").path("pbtrninfodscrt").asText();
        String information = directionRoot.path("response").path("body").path("items").path("item").path("mntninfodscrt").asText();
        String altitude = directionRoot.path("response").path("body").path("items").path("item").path("mntninfohght").asText();

        course.setDirection(direction);
        course.setInformation(information);
        course.setAltitude(altitude);

        // Course를 저장
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {

        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            courseRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("course not found");
        }
    }

    //next version
    @Override
    @Transactional
    public Course updateCourse(Long id) throws JsonProcessingException {
        return null;
    }
}
