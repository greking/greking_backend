package com.greking.Greking.Contents.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDto {

    private Long id;
    private String coursename;
    private String information;
    private String difficulty;
    private int duration;
    private int altitude;
    private String direction;

    private List<RestaurantDto> restaurant;
    private List<WeatherDto> weather;


}
