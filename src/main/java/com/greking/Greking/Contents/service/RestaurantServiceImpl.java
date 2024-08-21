package com.greking.Greking.Contents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantService restaurantService;


    @Autowired
    public RestaurantServiceImpl(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
}
