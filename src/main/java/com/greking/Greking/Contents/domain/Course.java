package com.greking.Greking.Contents.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //mountain FK
    @ManyToOne
    @JoinColumn(name = "mountain_id") //외래키 칼럼이름 지정
    private Mountain mountain;

    private String mountainName; //산이름

    private String courseName;

    @Column(name = "information", columnDefinition = "TEXT")
    private String information;
    private String difficulty; //난이도
    private int duration; //소요시간 (분단위)
    private int distance; //구간길이
    private int altitude; //고도 (미터 단위)


    @Column(name = "direction", columnDefinition = "TEXT")
    private String direction; //찾아오는 길

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Weather> weathers;

    // Course 생성 시 산 이름을 설정하는 빌더 메서드
    public static Course createCourse(Mountain mountain, String courseName, String information, String difficulty,
                                      int distance, int duration, int altitude, int ascendTime, int descendTime, String direction) {
        return Course.builder()
                .mountain(mountain)
                .mountainName(mountain.getName()) // 산 이름을 설정
                .courseName(courseName)
                .information(information)
                .difficulty(difficulty)
                .distance(distance)
                .duration(duration)
                .altitude(altitude)
                .direction(direction)
                .build();
    }

}
