package com.greking.Greking.Contents.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mountain_id") //외래키 칼럼이름 지정
    private Mountain mountain;

    @ManyToOne
    @JoinColumn(name = "course_id") //외래키 칼럼이름 지정
    private Course course;

    //저장 변수
    private String name;
    private String address;

    @ElementCollection
    @CollectionTable(name = "restaurant_images", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> imageUrls;

    private int tel; //전화번호

    private int mapX; //식당 정보에 필요한 좌표값
    private int mapY;

}
