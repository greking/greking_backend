package com.greking.Greking.Contents.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class ApiClient {
    private final RestTemplate restTemplate;

    public ApiClient(RestTemplate restTemplate){ //appconfig에서 restTemplate bean등록
        this.restTemplate = restTemplate;
    }


    public String fetchMountainData(String mountainName, double latitude, double longitude) {
        String serviceKey = "C3310E09-764F-3E00-9834-F6D12AD80CE4";

        // BOX 필터를 사용하여 좌표를 설정합니다.
        double minX = longitude - 0.01;
        double minY = latitude - 0.01;
        double maxX = longitude + 0.01;
        double maxY = latitude + 0.01;

        String geomFilter = String.format("BOX(%f %f, %f %f)", minX, minY, maxX, maxY);
        String attrFilter = String.format("category:%s", mountainName);

        String url = String.format(
                "https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_L_FRSTCLIMB&key=%s&geomfilter=%s&attrfilter=%s",
                serviceKey, geomFilter, attrFilter
        );

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch data from the API");
        }
    }


    public String fetchInfoDirectionData(String mountainName){
        String serviceKey = "DASXubxpgDMIa5rK0gegg8C8RA8J5qTojKbD1JyujPBeWWSwRLDCY1jt7lgcXlyKnd37RWdyfZ6bQAjcdJtM4g==";
        String url = String.format(
                "http://openapi.forest.go.kr/openapi/service/trailInfoService/getforeststoryservice?mntnNm=%s&ServiceKey=%s",
                mountainName,serviceKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }
        else{
         throw new RuntimeException("Failed to fetch direction & informaion from the API");
        }
    }

    // 중기육상예보조회: 날짜, 날씨상태, 강수확률
    public String fetchWeatherData(String regId2) {
        String serviceKey = "DASXubxpgDMIa5rK0gegg8C8RA8J5qTojKbD1JyujPBeWWSwRLDCY1jt7lgcXlyKnd37RWdyfZ6bQAjcdJtM4g==";
        String tmFc = getTmFc(); // 발표시각을 가져오는 메서드, 예: 202308240600

        String url = String.format(
                "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=%s&pageNo=1&numOfRows=10&dataType=JSON&regId=%s&tmFc=%s",
                serviceKey, regId2, tmFc);


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch weather data from the API");
        }
    }


    // 중기기온조회: 기온 데이터 가져오기
    public String fetchWeatherTemp(String regId1) {
        String serviceKey = "DASXubxpgDMIa5rK0gegg8C8RA8J5qTojKbD1JyujPBeWWSwRLDCY1jt7lgcXlyKnd37RWdyfZ6bQAjcdJtM4g==";
        String tmFc = getTmFc(); // 발표시각을 가져오는 메서드, 예: 202308240600

        String url = String.format(
                "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=%s&pageNo=1&numOfRows=10&dataType=JSON&regId=%s&tmFc=%s",
                serviceKey, regId1, tmFc);


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch temperature data from the API");
        }
    }


    // 발표시각 (tmFc) 가져오는 메서드
    private String getTmFc() {
        LocalDateTime now = LocalDateTime.now();

        if (now.getHour() < 6) {
            // 현재 시간이 00시부터 05시 59분까지인 경우, 전날 18시로 설정
            return now.minusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0)
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } else {
            // 나머지 경우, 오늘 06시로 설정
            return now.withHour(6).withMinute(0).withSecond(0).withNano(0)
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
    }


    //식당정보 가져오는 메서드
    public String fetchRestaurantInfo(double longitude, double latitude) {
        String serviceKey = "DASXubxpgDMIa5rK0gegg8C8RA8J5qTojKbD1JyujPBeWWSwRLDCY1jt7lgcXlyKnd37RWdyfZ6bQAjcdJtM4g==";
        String url = String.format(
                "https://apis.data.go.kr/B551011/KorService1/locationBasedList1?numOfRows=10&MobileOS=AND&MobileApp=MobileApp&_type=json&mapX=%f&mapY=%f&radius=50000&contentTypeId=39&serviceKey=%s",
                longitude, latitude, serviceKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch restaurant data from the API");
        }
    }
}
