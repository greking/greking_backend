package com.greking.Greking.User.repository;


import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {


    Optional<UserCourse> findById(Long usercourseId);

    List<UserCourse> findByUser(User user);

    void delete(UserCourse userCourse);

}
