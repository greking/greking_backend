package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByMountain(Mountain mountain);

    Course findByCourseName(String coursename);

    List<Course> findByDifficulty(String difficulty);
}
