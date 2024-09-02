package com.greking.Greking.Review.domain;


import com.greking.Greking.User.domain.User;
import com.greking.Greking.User.domain.UserCourse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    //id값, 리뷰, 별점, 난이도

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int review_score;
    private String review_difficulty;
    private String review_text;

    private String courseName;
    private String nickName;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="usercourse_id")
    private UserCourse userCourse;


}
