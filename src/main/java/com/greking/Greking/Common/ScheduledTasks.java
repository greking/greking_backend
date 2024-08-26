package com.greking.Greking.Common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.service.CourseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private final CourseService courseService;

    public ScheduledTasks(CourseService courseService) {
        this.courseService = courseService;
    }

    @Scheduled(cron = "0 0 6 * *?")
    public void updateWeatherAndRestauranData() throws JsonProcessingException{
        List<CourseDto> allCourses = courseService.getAllCourses();

        for (CourseDto course : allCourses) {
            courseService.updateCourse(course.getId());
        }
    }
}
