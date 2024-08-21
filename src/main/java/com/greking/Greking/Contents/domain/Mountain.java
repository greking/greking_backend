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
@Table(name = "mountain_entity")
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "mountain", cascade = CascadeType.ALL)
    private List<Course> course;

}
