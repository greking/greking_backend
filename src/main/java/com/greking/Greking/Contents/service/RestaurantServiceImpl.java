package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.repository.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MountainRepository mountainRepository;
    private final ApiClient apiClient;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, MountainRepository mountainRepository, ApiClient apiClient) {
        this.restaurantRepository = restaurantRepository;
        this.mountainRepository = mountainRepository;
        this.apiClient = apiClient;
    }

    @Override
    public void fetchAndSaveRestaurantData(Long mountainId) throws JSONException {
        // 산의 주소로 Mountain 객체를 조회
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(() -> new RuntimeException("해당 주소의 산을 찾을 수 없습니다."));

        // 외부 API를 통해 식당 정보 가져오기
        String restaurantData = apiClient.fetchRestaurantInfo(mountain.getLongitude(), mountain.getLatitude());

        // 데이터 파싱 및 Restaurant 객체에 매핑
        List<Restaurant> restaurants = parseRestaurantData(restaurantData, mountain);

        // Restaurant 객체를 DB에 저장
        restaurantRepository.saveAll(restaurants);
    }

    @Override
    public List<Restaurant> getRestaurantsByMountain(Long mountainId) {
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(() -> new RuntimeException("해당 산을 찾을 수 없습니다."));

        return restaurantRepository.findByMountain(mountain);
    }



    // 파싱 로직 예시
    private List<Restaurant> parseRestaurantData(String restaurantData, Mountain mountain) throws JSONException {
        List<Restaurant> restaurantList = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(restaurantData);
        JSONArray items = jsonResponse.getJSONObject("response").getJSONObject("body").getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            Restaurant restaurant = new Restaurant();
            restaurant.setMountain(mountain);
            restaurant.setName(item.getString("title"));
            restaurant.setAddress(item.getString("addr1"));
            restaurant.setTel(item.optString("tel", ""));
            restaurant.setMapX(item.getInt("mapx"));
            restaurant.setMapY(item.getInt("mapy"));

            restaurantList.add(restaurant);
        }

        return restaurantList;
    }
}
