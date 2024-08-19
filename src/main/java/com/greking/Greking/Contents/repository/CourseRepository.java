package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
