package com.greking.Greking.Contents.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ApiClient {
    private final RestTemplate restTemplate;

    public ApiClient(RestTemplate restTemplate){ //appconfig에서 restTemplate bean등록
        this.restTemplate = restTemplate;
    }


    //다시작업 -> naver map api로 변경
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

}
