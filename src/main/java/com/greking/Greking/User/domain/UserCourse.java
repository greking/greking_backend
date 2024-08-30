package com.greking.Greking.User.domain;

import com.greking.Greking.Contents.domain.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String difficulty; //코스 난이도

    private LocalDateTime addedAt; //코스를 담은 시점

    private String distance; // 이동거리 (킬로미터 단위)

    private String calories; // 소모된 칼로리 (kcal 단위)

    private String duration; // 움직인 시간 (초 단위)

    private String courseName; //코스명

    @Column(name = "altitude")
    private String altitude; //움직인 고도


    @Column(name = "status", nullable = false)
    private String status = "예정"; // 등산완료했는지 check
}
