package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.GPXFile;
import com.greking.Greking.Contents.repository.GPXRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GPXService {

    private GPXRepository gpxRepository;
    @Autowired
    public GPXService(GPXRepository gpxRepository) {
        this.gpxRepository = gpxRepository;
    }

    public GPXFile findGPXByCourseName(String courseName) {
        return gpxRepository.findByCourseName(courseName).orElse(null);
    }

    public void saveFileToDB(String fileName, byte[] fileData) {
        GPXFile gpxFile = new GPXFile();

        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        gpxFile.setCourseName(fileName);  // 파일 이름을 course_name으로 설정
        gpxFile.setGpxData(fileData);  // 파일 데이터를 byte[]로 설정
        gpxRepository.save(gpxFile);
    }
}

