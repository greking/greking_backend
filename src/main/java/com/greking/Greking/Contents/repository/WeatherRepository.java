package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
