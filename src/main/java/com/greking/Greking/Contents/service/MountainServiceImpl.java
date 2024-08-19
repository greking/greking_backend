package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.dto.MountainDto;
import com.greking.Greking.Contents.repository.MountainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MountainServiceImpl implements MountainService{

    private final MountainRepository mountainRepository;
    private final CourseService courseService;

    @Autowired
    public MountainServiceImpl(MountainRepository mountainRepository, CourseService courseService){
        this.mountainRepository = mountainRepository;
        this.courseService = courseService;
    }


    @Override
    public MountainDto saveMountainWithCourse(MountainDto mountainDto) {
        Mountain mountain = converToEntity(mountainDto);
        mountain.setName(mountainDto.getName());

    }

    @Override
    public MountainDto getMountainById(Long id) {
        return null;
    }

    @Override
    public List<Mountain> getAllMountains() {
        return null;
    }

    @Override
    public void deleteMountain(Long id) {

    }

    private MountainDto converToEntity(Mountain mountain){
        return MountainDto.builder()
                .id(mountain.getId())
                .name(mountain.getName())
                .courses(mountain.getCourse().stream()
                        .map(course -> CourseDto.builder()
                                .id(course.getId))
    }
}
