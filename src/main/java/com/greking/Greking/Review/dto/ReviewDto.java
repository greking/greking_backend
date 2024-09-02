package com.greking.Greking.Review.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;

    private int review_score;
    private String review_difficulty;
    private String review_text;

    private String courseName;
    private String nickName;


}
