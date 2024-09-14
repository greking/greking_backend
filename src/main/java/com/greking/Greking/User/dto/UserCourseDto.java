package com.greking.Greking.User.dto;

import com.greking.Greking.Contents.domain.Course;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserCourseDto {


    private Long userCourseId;

    private Course course;

    private String difficulty; //코스 난이도

    private LocalDateTime addedAt; //코스를 담은 시점

    private String distance; // 이동거리 (킬로미터 단위)

    private String calories; // 소모된 칼로리 (kcal 단위)

    private String duration; // 움직인 시간 (초 단위)

    private String courseName; //코스명

    private String altitude; //움직인 고도

    private String status;
}
