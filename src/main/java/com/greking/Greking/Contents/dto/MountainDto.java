package com.greking.Greking.Contents.dto;


import com.greking.Greking.Contents.domain.Course;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainDto {

    private Long id;
    private String name;

    private double latitude;  // 위도
    private double longitude; // 경도

    private List<Course> course;


}
