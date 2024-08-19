package com.greking.Greking.Contents.controller;

import com.greking.Greking.Contents.dto.MountainDto;
import com.greking.Greking.Contents.service.MountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mountains")
public class MountainController {

    private final MountainService mountainService;

    @Autowired
    public MountainController(MountainService mountainService){
        this.mountainService = mountainService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MountainDto> getMountainById(@PathVariable Long id){
        MountainDto mountainDto = mountainService.getMountainById(id);


    }


}
