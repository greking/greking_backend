package com.greking.Greking.Contents.service;


import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.MountainDto;

import java.util.List;

public interface MountainService {
    MountainDto getMountainById(Long id);
    List<Mountain> getAllMountains();

    Mountain saveMountainWithCourse(Mountain mountain);

    void deleteMountain(Long id);

}
