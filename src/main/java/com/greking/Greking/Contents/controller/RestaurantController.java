package com.greking.Greking.Contents.controller;


import com.greking.Greking.Contents.dto.RestaurantDto;
import com.greking.Greking.Contents.service.RestaurantService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;


    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping("/getInfo")
    public ResponseEntity<List<RestaurantDto>> getRestaurantsByMountain(@RequestParam(name = "courseId") Long courseId) {
        try {
            List<RestaurantDto> restaurants = restaurantService.getRestaurantsByCourse(courseId);
            if (restaurants.isEmpty()) {
                return ResponseEntity.noContent().build(); // 데이터가 없을 경우 204 응답
            }
            return ResponseEntity.ok(restaurants); // 데이터가 있을 경우 200 응답과 함께 데이터 반환
        } catch (Exception e) {
            System.out.println("Error occurred while fetching restaurants: " + e.getMessage());
            return ResponseEntity.status(404).body(null); // 예외가 발생하면 404 응답
        }
    }

    @PostMapping("/fetch/{courseId}")
    public ResponseEntity<String> fetchAndSaveRestaurants(@PathVariable Long courseId) throws JSONException {
        try {
            restaurantService.saveRestaurantData(courseId);
            return ResponseEntity.ok("Restaurant data successfully fetched and saved.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 전체 산의 식당 정보를 업데이트하는 엔드포인트
    @PostMapping("/fetch-and-save-all/")
    public ResponseEntity<String> fetchAndSaveAllRestaurantData(){
        try{
            restaurantService.saveAllRestaurantData();
            return ResponseEntity.ok("Restaurant data for all mountains successfully fetched and saved.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Failed to fetch and save Restaurant data for all mountains: " + e.getMessage());
        }
    }


}
