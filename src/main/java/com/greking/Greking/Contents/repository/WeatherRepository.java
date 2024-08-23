package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    // 예보구역코드 (cityCode)로 날씨 정보를 조회하는 메서드
    Optional<Weather> findByCityCode(String cityCode);

    // 예보구역코드 (westEastCode)로 날씨 정보를 조회하는 메서드
    Optional<Weather> findByWestEastCode(String westEastCode);
}
