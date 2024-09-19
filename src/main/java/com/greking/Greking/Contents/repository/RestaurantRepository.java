package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    boolean existsByCourseAndNameAndAddress(Course course, String name, String address);

    long countByMountainMountainId(Long mountainId);


    List<Restaurant> findByCourseCourseName(String courseName);
}
