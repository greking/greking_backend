package com.greking.Greking.Contents.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "mountain")
    private List<Course> course;

    @OneToMany(mappedBy = "mountain")
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "mountain")
    private List<Weather> weather;



}
