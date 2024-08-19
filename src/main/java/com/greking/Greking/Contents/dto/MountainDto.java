package com.greking.Greking.Contents.dto;


import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.domain.Weather;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainDto {

    private Long id;
    private String name;
    private List<Course> course;
    private List<RestaurantDto> restaurant;
    private List<WeatherDto> weather;


}
