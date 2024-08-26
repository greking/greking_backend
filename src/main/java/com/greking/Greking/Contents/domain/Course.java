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

}
