package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.dto.CourseDto;

import java.util.List;

public interface CourseService {

    //코스정보 가져오기
    //코스 저장하기
    //코스 수정하기
    //코스 삭제하기
    //모든 코스 정보 가져오기

    CourseDto getCourseById(Long id);
    List<Course> getAllCourses();

    //비공개 api
    Course saveCourse(Course course);
    Course updateCourse(Long id);
    void deleteCourse(Long id);


}
