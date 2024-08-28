package com.greking.Greking.Contents.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore  // 이 필드를 직렬화에서 제외
    @JsonBackReference  // 자식 쪽에 추가
    private Mountain mountain;

    private String mountainName; //산이름

    @Column(name = "information", columnDefinition = "TEXT")
    private String information; //산정보

    private String courseName; //코스이름

    @Column(name = "course_info", columnDefinition = "TEXT")
    private String course_info; //코스정보
    private String difficulty; //난이도
    private String duration; //소요시간 (분단위)
    private String distance; //구간길이
    private String altitude; //고도

    //경도
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double longitude = 0.0;

    //위도
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double latitude = 0.0;


    @Column(name = "direction", columnDefinition = "TEXT")
    private String direction; //찾아오는 길

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Weather> weathers;

}
