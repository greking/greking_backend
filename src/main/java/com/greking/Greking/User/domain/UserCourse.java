package com.greking.Greking.User.domain;

import com.greking.Greking.Contents.domain.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserCourse")
public class UserCourse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private Course course;


    private LocalDateTime addedAt; //코스를 담은 시점

    private double distance; // 이동거리 (킬로미터 단위)

    private double calories; // 소모된 칼로리 (kcal 단위)

    private long duration; // 움직인 시간 (초 단위)
}
