package com.greking.Greking.Contents.controller;


import com.greking.Greking.Contents.domain.Restaurant;
import com.greking.Greking.Contents.service.RestaurantService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;


    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{mountainId}/")
    public List<Restaurant> getRestaurantsByMountain(@PathVariable Long mountainId) {
        return restaurantService.getRestaurantsByMountain(mountainId);
    }

    @PostMapping("/fetch/{mountainId}")
    public void fetchAndSaveRestaurants(@PathVariable Long mountainId) throws JSONException {
        restaurantService.fetchAndSaveRestaurantData(mountainId);
    }

}
