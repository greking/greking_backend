package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Restaurant;
import org.json.JSONException;

import java.util.List;

public interface RestaurantService {
    // 외부 API에서 식당 데이터를 가져와 저장하는 메서드
    void fetchAndSaveRestaurantData(Long mountainId) throws JSONException;

    // 특정 산 주변의 식당 목록을 조회하는 메서드
    List<Restaurant> getRestaurantsByMountain(Long mountainId);

}
