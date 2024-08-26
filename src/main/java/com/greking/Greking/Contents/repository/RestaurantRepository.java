package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {



    boolean existsByMountainAndNameAndAddress(Mountain mountain, String name, String address);
    long countByMountainId(Long mountainId);


    List<Restaurant> findByMountainId(Long mountainId);
}
