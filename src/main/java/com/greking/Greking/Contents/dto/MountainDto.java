package com.greking.Greking.Contents.dto;


import com.greking.Greking.Contents.domain.Course;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainDto {

    private Long mountainId;
    private String name;
    private String addressState; //대표도시

    private double latitude;  // 위도
    private double longitude; // 경도

    private String cityCode; //도시코드
    private String westEastCode; //영서 / 영동지방

    private List<Course> course;


}
