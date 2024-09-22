package com.greking.Greking.Review.repository;

import com.greking.Greking.Review.domain.Review;
import com.greking.Greking.Review.dto.ReviewDto;
import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByUserAndUserCourse(User user, UserCourse UserCourse);

    List<Review> findByCourseCourseName(String courseName);

    List<Review> findByUser(User user);


}
