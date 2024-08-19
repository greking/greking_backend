package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
