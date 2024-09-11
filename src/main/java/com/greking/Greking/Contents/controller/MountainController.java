package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.domain.Mountain;
import com.greking.Greking.Contents.dto.MountainDto;
import com.greking.Greking.Contents.service.MountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mountains")
public class MountainController {

    private final MountainService mountainService;

    @Autowired
    public MountainController(MountainService mountainService){
        this.mountainService = mountainService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMountainById(@PathVariable Long id){
        try{
            mountainService.getMountainById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMountain(){
        try{
            List<MountainDto> mountains = mountainService.getAllMountains().stream()
                    .map(mountain -> MountainDto.builder()
                            .mountainId(mountain.getMountainId())
                            .name(mountain.getName())
                            .addressState(mountain.getAddressState())
                            .latitude(mountain.getLatitude())
                            .longitude(mountain.getLongitude())
                            .cityCode(mountain.getCityCode())
                            .westEastCode(mountain.getWestEastCode())
                            .course(mountain.getCourse())
                            .build())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(mountains, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //산정보 저장 및 삭제
    //비공개 api
    @PostMapping
    public ResponseEntity<?>saveMountainWithCourse(@RequestBody Mountain mountain){
        try{
            Mountain createdMountain = mountainService.saveMountainWithCourse(mountain);
            return new ResponseEntity<>(createdMountain, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMountain(@PathVariable Long id){
        try{
            mountainService.deleteMountain(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
