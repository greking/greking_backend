package com.greking.Greking.Contents.dto;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDto {

    private Long courseId;
    private String mountainName; //산이름
    private String information; //산정보

    private String courseImage; //코스이미지
    private String courseName; //코스이름
    private String course_info; //코스정보
    private String difficulty; //난이도
    private String distance; //구간길이
    private String duration; //소요시간
    private String altitude; //고도

    @Builder.Default
    private double longitude = 0.0; //출발지 위도
    @Builder.Default
    private double latitude = 0.0; //출발지 경도
    private String direction; //찾아오는길


}
