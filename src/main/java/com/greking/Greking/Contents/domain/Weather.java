package com.greking.Greking.Contents.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "mountain_id") //외래키 칼럼이름 지정
    private Mountain mountain;

    @ManyToOne
    @JoinColumn(name = "course_id") //외래키 칼럼이름 지정
    private Course course;

    private String regId; //지역별 날씨상태, 강수확률가져오기
    private String regId2; //지역별 온도 가져오기

    private LocalDate forecastDate;
    private String condition; //날씨 상태 (맑음, 흐림)
    private float temparature;
    private int predictRain; //강수확률
    private LocalDateTime tmFc;

}
