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

    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double longitude = 0.0;

    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double latitude = 0.0;

    @OneToMany(mappedBy = "mountain", cascade = CascadeType.ALL)
    private List<Course> course;

}
