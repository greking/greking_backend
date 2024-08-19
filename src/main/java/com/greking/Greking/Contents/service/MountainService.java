package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.MountainDto;

import java.util.List;

public interface MountainService {
    MountainDto saveMountainWithCourse(MountainDto mountainDto);
    MountainDto getMountainById(Long id);
    List<Mountain> getAllMountains();

    void deleteMountain(Long id);

}
