package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.dto.RestaurantDto;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.repository.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void saveRestaurantData(Long mountainId) throws JSONException {
        // 산의 ID로 Mountain 객체를 조회
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(() -> new RuntimeException("해당 산을 찾을 수 없습니다."));

        // 이미 등록된 식당의 수를 확인
        long restaurantCount = restaurantRepository.countByMountainId(mountainId);
        if (restaurantCount >= 5) {
            System.out.println("이미 최대 5개의 식당이 등록되어 있습니다.");
            return;
        }

        // 외부 API를 통해 식당 정보 가져오기
        String restaurantData = apiClient.fetchRestaurantInfo(mountain.getLongitude(), mountain.getLatitude());

        // 데이터 파싱 및 Restaurant 객체에 매핑
        List<Restaurant> restaurants = parseRestaurantData(restaurantData, mountain);

        // 중복되지 않은 최대 5개의 식당만 저장
        for (Restaurant restaurant : restaurants) {
            if (restaurantCount >= 5) {
                break; // 이미 5개가 저장되었다면 추가하지 않음
            }

            // 중복 체크: 이름과 주소로 식별
            boolean exists = restaurantRepository.existsByMountainAndNameAndAddress(
                    mountain, restaurant.getName(), restaurant.getAddress());

            if (!exists) {
                // 필드가 모두 null이 아닌지 확인 (예: name, address, tel, imageUrl1)
                if (restaurant.getName() != null && restaurant.getAddress() != null && restaurant.getTel() != null && restaurant.getImageUrl1() != null) {
                    restaurant.setMapX(mountain.getLongitude());
                    restaurant.setMapY(mountain.getLatitude());

                    // Restaurant 객체를 DB에 저장
                    restaurantRepository.save(restaurant);
                    restaurantCount++; // 저장된 식당 수 증가
                } else {
                    System.out.println("Restaurant data contains null values, not saving to DB.");
                }
            } else {
                System.out.println("Duplicate restaurant found, not saving to DB.");
            }
        }
    }

    @Override
    public List<RestaurantDto> getRestaurantsByMountain(Long mountainId) {
        List<Restaurant> restaurants = restaurantRepository.findByMountainId(mountainId);
        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RestaurantDto convertToDto(Restaurant restaurant) {
        return RestaurantDto.builder()
                .id(restaurant.getId())
                .restaurant_name(restaurant.getName())
                .tel(restaurant.getTel())
                .address(restaurant.getAddress())
                .mapX(restaurant.getMapX())
                .mapY(restaurant.getMapY())
                .imageUrl1(restaurant.getImageUrl1())
                .imageUrl2(restaurant.getImageUrl2())
                .build();
    }


    // 파싱 로직: 외부 API의 JSON 응답을 List<Restaurant>로 변환
    private List<Restaurant> parseRestaurantData(String restaurantData, Mountain mountain) throws JSONException {
        List<Restaurant> restaurantList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(restaurantData);
            JSONObject InfoBody = jsonResponse.getJSONObject("response").getJSONObject("body");
            JSONArray InfoItems = InfoBody.getJSONObject("items").getJSONArray("item");

            for (int i = 0; i < InfoItems.length(); i++) {
                JSONObject InfoItem = InfoItems.getJSONObject(i);

                Restaurant restaurant = new Restaurant();
                restaurant.setMountain(mountain);
                restaurant.setName(InfoItem.getString("title"));
                restaurant.setAddress(InfoItem.getString("addr1"));
                restaurant.setTel(InfoItem.getString("tel"));
                restaurant.setImageUrl1(InfoItem.optString("firstimage", null));
                restaurant.setImageUrl2(InfoItem.optString("firstimage2", null));

                restaurantList.add(restaurant);
            }

        } catch (JSONException e) {
            System.out.println("Failed to parse Restaurant data: " + e.getMessage());
        }

        return restaurantList;
    }

    @Override
    public void saveAllRestaurantData() {
        List<Mountain> mountains = mountainRepository.findAll();

        for (Mountain mountain : mountains) {
            try {
                saveRestaurantData(mountain.getId());
            } catch (JSONException e) {
                System.out.println("Failed to fetch Restaurant data for mountain: " + mountain.getName() + " - " + e.getMessage());
            }
        }
    }
}
