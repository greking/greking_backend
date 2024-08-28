package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.domain.Weather;
import com.greking.Greking.Contents.dto.WeatherDto;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.Contents.repository.WeatherRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    public void saveWeatherData(Long mountainId) throws JSONException {
        // 산의 주소로 Mountain 객체를 조회
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(() -> new RuntimeException("해당 주소의 산을 찾을 수 없습니다."));

        // 중기전망조회 API 호출
        String forecastData = apiClient.fetchWeatherData(mountain.getWestEastCode());

        // 중기기온조회 API 호출
        String temperatureData = apiClient.fetchWeatherTemp(mountain.getCityCode());

        // 데이터 파싱 및 Weather 객체에 매핑
        Weather weather = parseWeatherData(forecastData, temperatureData);
        weather.setMountain(mountain);
        weather.setCityCode(mountain.getCityCode());
        weather.setWestEastCode(mountain.getWestEastCode());
        weather.setMountainName(mountain.getName());
        weather.setAddressState(mountain.getAddressState());
        weather.setTmFc(LocalDateTime.now());

        weather.setForecastDate3(LocalDate.now().plusDays(3));
        weather.setForecastDate4(LocalDate.now().plusDays(4));
        weather.setForecastDate5(LocalDate.now().plusDays(5));
        weather.setForecastDate6(LocalDate.now().plusDays(6));
        weather.setForecastDate7(LocalDate.now().plusDays(7));


        // 추가적으로 파싱된 데이터를 Weather 객체에 설정하고 DB에 저장
        weatherRepository.save(weather);
        System.out.println(weather.getAddressState());
    }

    @Override
    public WeatherDto getWeatherForecast(Long mountainId) {
        // 산의 id를 기반으로 DB에서 산을 조회
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 산을 찾을 수 없습니다."));

        Weather weather = weatherRepository.findByMountain(mountain);
        return convertToDto(weather);
    }

    private WeatherDto convertToDto(Weather weather) {
        return WeatherDto.builder()
                .id(weather.getId())
                .mountainName(weather.getMountainName())
                .addressState(weather.getAddressState())
                .tmFc(weather.getTmFc())
                .westEastCode(weather.getWestEastCode())
                .cityCode(weather.getCityCode())
                .forecastDate3(weather.getForecastDate3())
                .condition3(weather.getCondition3())
                .temperature3(weather.getTemperature3())
                .predictRain3(weather.getPredictRain3())
                .forecastDate4(weather.getForecastDate4())
                .condition4(weather.getCondition4())
                .temperature4(weather.getTemperature4())
                .predictRain4(weather.getPredictRain4())
                .forecastDate5(weather.getForecastDate5())
                .condition5(weather.getCondition5())
                .temperature5(weather.getTemperature5())
                .predictRain5(weather.getPredictRain5())
                .forecastDate6(weather.getForecastDate6())
                .condition6(weather.getCondition6())
                .temperature6(weather.getTemperature6())
                .predictRain6(weather.getPredictRain6())
                .forecastDate7(weather.getForecastDate7())
                .condition7(weather.getCondition7())
                .temperature7(weather.getTemperature7())
                .predictRain7(weather.getPredictRain7())
                .build();
    }

    @Override
    public void saveAllWeatherData() {
        List<Mountain> mountains = mountainRepository.findAll();

        for (Mountain mountain : mountains){
            try{
                // 중복 여부를 확인하기 위한 로직 추가
                boolean exists = weatherRepository.existsByMountainId(mountain.getId());

                if (!exists) {
                    // 날씨 정보가 없는 경우에만 데이터를 저장
                    saveWeatherData(mountain.getId());
                } else {
                    System.out.println("Weather data already exists for mountain: " + mountain.getName());
                }
            } catch (JSONException e) {
                System.out.println("Failed to fetch weather data for mountain: " + mountain.getName() + " - " + e.getMessage());
            }
        }
    }

    // JSON 파싱 메서드
    private Weather parseWeatherData(String forecastData, String temperatureData) {
        Weather weather = new Weather();
        try{
            // 중기전망 데이터 파싱 (날씨 상태, 강수확률)
            JSONObject forecastJson = new JSONObject(forecastData);
            JSONObject forecastBody = forecastJson.getJSONObject("response").getJSONObject("body");
            JSONArray forecastItems = forecastBody.getJSONObject("items").getJSONArray("item");

            // 첫 번째 요소를 사용하여 파싱 (실제로는 필요한 데이터에 맞게 처리)
            if (forecastItems.length() > 0) {
                JSONObject forecastItem = forecastItems.getJSONObject(0);

                // 날씨 상태 및 강수확률 설정
                weather.setCondition3(forecastItem.getString("wf3Am"));
                weather.setPredictRain3(forecastItem.getInt("rnSt3Am"));

                weather.setCondition4(forecastItem.getString("wf4Am"));
                weather.setPredictRain4(forecastItem.getInt("rnSt4Am"));

                weather.setCondition5(forecastItem.getString("wf5Am"));
                weather.setPredictRain5(forecastItem.getInt("rnSt5Am"));

                weather.setCondition6(forecastItem.getString("wf6Am"));
                weather.setPredictRain6(forecastItem.getInt("rnSt6Am"));

                weather.setCondition7(forecastItem.getString("wf7Am"));
                weather.setPredictRain7(forecastItem.getInt("rnSt7Am"));
            }

        } catch (JSONException e){
            System.out.println("Failed to parse forecast data: " + e.getMessage());
        }

        try{
            // 중기기온 데이터 파싱 (기온)
            JSONObject temperatureJson = new JSONObject(temperatureData);
            JSONObject temperatureBody = temperatureJson.getJSONObject("response").getJSONObject("body");
            JSONArray temperatureItems = temperatureBody.getJSONObject("items").getJSONArray("item");

            if (temperatureItems.length() > 0) {
                JSONObject temperatureItem = temperatureItems.getJSONObject(0);

                // 각 날짜별 기온의 평균값 설정 (예상 최저기온과 예상 최고기온의 평균)
                weather.setTemperature3(calculateAverageTemperature(
                        (float) temperatureItem.getDouble("taMin3"),
                        (float) temperatureItem.getDouble("taMax3")
                ));

                weather.setTemperature4(calculateAverageTemperature(
                        (float) temperatureItem.getDouble("taMin4"),
                        (float) temperatureItem.getDouble("taMax4")
                ));

                weather.setTemperature5(calculateAverageTemperature(
                        (float) temperatureItem.getDouble("taMin5"),
                        (float) temperatureItem.getDouble("taMax5")
                ));

                weather.setTemperature6(calculateAverageTemperature(
                        (float) temperatureItem.getDouble("taMin6"),
                        (float) temperatureItem.getDouble("taMax6")
                ));

                weather.setTemperature7(calculateAverageTemperature(
                        (float) temperatureItem.getDouble("taMin7"),
                        (float) temperatureItem.getDouble("taMax7")
                ));
            }

        } catch (JSONException e){
            System.out.println("Failed to parse temperature data: " + e.getMessage());
        }

        return weather;
    }

    //날씨 기온: 예상 최저기온, 최고기온의 평균값
    private int calculateAverageTemperature(float minTemp, float maxTemp) {
        float average = (minTemp + maxTemp) / 2;
        return Math.round(average); // 반올림하여 정수로 반환
    }

}
