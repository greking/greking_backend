package com.greking.Greking.Contents.service;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.CourseDto;
import com.greking.Greking.Contents.dto.MountainDto;
import com.greking.Greking.Contents.repository.MountainRepository;
import com.greking.Greking.User.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MountainServiceImpl implements MountainService{

    private final MountainRepository mountainRepository;

    @Autowired
    public MountainServiceImpl(MountainRepository mountainRepository){
        this.mountainRepository = mountainRepository;
    }


    @Override
    public MountainDto getMountainById(Long id) {
        //Id로 조회
        Mountain mountain = mountainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mountain not found"));

        //조회된 mountain entity를 mountainDto로 변환하여 반환
        return convertToDto(mountain);
    }

    @Override
    public List<Mountain> getAllMountains() {
        return mountainRepository.findAll();
    }

    private MountainDto convertToDto(Mountain mountain){
        return MountainDto.builder()
                .mountainId(mountain.getMountainId())
                .name(mountain.getName())
                .addressState(mountain.getAddressState())
                .cityCode(mountain.getCityCode())
                .westEastCode(mountain.getWestEastCode())
                .latitude(mountain.getLatitude())
                .longitude(mountain.getLongitude())
                .course(mountain.getCourse())
                .build();
    }

    //비공개 서비스 로직
    @Override
    public Mountain saveMountainWithCourse(Mountain mountain) {

        //각 course의 mountain 설정
        mountain.getCourse().forEach(course -> course.setMountain(mountain));

        //mountain 엔티티와 연관된 course 엔티티를 저장
        return mountainRepository.save(mountain);
    }

    @Override
    public void deleteMountain(Long id) {
        Optional<Mountain> mountainOptional = mountainRepository.findById(id);
        if (mountainOptional.isPresent()) {
            mountainRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Mountain not found.");
        }
    }

}
