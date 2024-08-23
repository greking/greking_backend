package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final MountainRepository mountainRepository;
    private final ApiClient apiClient;

    public WeatherServiceImpl(WeatherRepository weatherRepository, MountainRepository mountainRepository, ApiClient apiClient) {
        this.weatherRepository = weatherRepository;
        this.mountainRepository = mountainRepository;
        this.apiClient = apiClient;
    }

    @Override
    public void fetchAndSaveWeatherData(String mountainAddress) {
        // 산의 주소로 Mountain 객체를 조회
        Mountain mountain = mountainRepository.findByAddressState(mountainAddress)
                .orElseThrow(() -> new RuntimeException("해당 주소의 산을 찾을 수 없습니다."));

        // 중기전망조회 API 호출
        String forecastData = apiClient.fetchWeatherData(mountain.getName(), mountain.getAddressState(), mountain.getWestEastCode());

        // 중기기온조회 API 호출
        String temperatureData = apiClient.fetchWeatherTemp(mountain.getName(), mountain.getAddressState(), mountain.getCityCode());

        // 데이터 파싱 및 Weather 객체에 매핑 (파싱 로직 추가 필요)
        Weather weather = new Weather();
        weather.setMountain(mountain);
        weather.setCityCode(mountain.getCityCode());
        weather.setWestEastCode(mountain.getWestEastCode());

        // 추가적으로 파싱된 데이터를 Weather 객체에 설정하고 DB에 저장
        weatherRepository.save(weather);
    }

    @Override
    public Weather getWeatherForecast(String mountainAddress) {
        // 산의 주소를 기반으로 DB에서 날씨 정보를 조회
        Mountain mountain = mountainRepository.findByAddressState(mountainAddress)
                .orElseThrow(() -> new RuntimeException("해당 주소의 산을 찾을 수 없습니다."));

        return weatherRepository.findByCityCode(mountain.getCityCode())
                .orElseThrow(() -> new RuntimeException("해당 지역의 날씨 정보를 찾을 수 없습니다."));
    }
}
