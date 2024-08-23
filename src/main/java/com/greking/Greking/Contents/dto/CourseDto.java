package com.greking.Greking.Contents.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDto {

    private Long id;
    private String courseName;
    private String mountainName; //산이름
    private String information;
    private String difficulty;
    private int distance; //구간길이
    private int duration; //소요시간
    private int altitude; //고도
    private String direction; //찾아오는길

    private List<RestaurantDto> restaurant;
    private List<WeatherDto> weather;


}
