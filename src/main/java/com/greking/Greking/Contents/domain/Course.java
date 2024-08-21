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

    private String coursename;
    private String information;
    private String difficulty;
    private int duration; //소요시간 (분단위)
    private int altitude; //고도 (미터 단위)
    private String direction; //찾아오는 길

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Weather> weathers;

}
