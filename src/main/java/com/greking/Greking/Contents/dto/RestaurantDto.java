package com.greking.Greking.Contents.dto;

import lombok.*;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantDto {
    private Long id;
    private String restaurant_name;
    private String tel;
    private String address;

    private int mapX;
    private int mapY;
    private List<String> imageUrls;
}
