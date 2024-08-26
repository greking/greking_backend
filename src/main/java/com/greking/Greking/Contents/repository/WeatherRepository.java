package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Weather findByMountain(Mountain mountain);
}
