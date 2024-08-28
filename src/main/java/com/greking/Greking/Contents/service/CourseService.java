package com.greking.Greking.Contents.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.dto.CourseDto;

import java.util.List;

public interface CourseService {

    //코스정보 가져오기
    //코스 저장하기
    //코스 수정하기
    //코스 삭제하기
    //모든 코스 정보 가져오기

    CourseDto getCourseById(Long courseId);
    List<CourseDto> getAllCourses();

    //비공개 api
    void addAllCourses() throws JsonProcessingException;
    Course saveCourse(Course course) throws JsonProcessingException;
    Course updateCourse(Long id) throws JsonProcessingException;
    void deleteCourse(Long id);

}
