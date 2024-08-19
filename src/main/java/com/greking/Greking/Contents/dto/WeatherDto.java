package com.greking.Greking.Contents.dto;

import com.greking.Greking.Contents.domain.Mountain;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherDto {
    private Long id;
    private Mountain mountain;



}
