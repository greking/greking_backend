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
@Table(name = "mountain")
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String addressState;

    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double longitude = 0.0;

    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double latitude = 0.0;

    private String cityCode; //온도 가져오기 (기온)
    private String westEastCode; //강수확률, 날씨상태, 날짜 가져오기


    @OneToMany(mappedBy = "mountain", cascade = CascadeType.ALL)
    private List<Course> course;

}
