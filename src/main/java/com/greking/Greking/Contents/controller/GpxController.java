package com.greking.Greking.Contents.controller;


import com.greking.Greking.Contents.domain.GPXFile;
import com.greking.Greking.Contents.dto.DirectoryRequest;
import com.greking.Greking.Contents.service.GPXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/gpx")
public class GpxController {

    private final GPXService gpxService;

    @Autowired
    public GpxController(GPXService gpxService) {
        this.gpxService = gpxService;
    }

    @GetMapping("/{courseName}")
    public ResponseEntity<ByteArrayResource> getGPXFile(@PathVariable(name="courseName") String courseName) {
        GPXFile gpxFile = gpxService.findGPXByCourseName(courseName);

        if (gpxFile != null) {
            ByteArrayResource resource = new ByteArrayResource(gpxFile.getGpxData());

            return ResponseEntity.ok()
                    .contentLength(gpxFile.getGpxData().length)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + courseName + ".gpx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/upload_directory")
    public String uploadDirectory(@RequestBody DirectoryRequest request) {
        String directoryPath = request.getDirectoryPath();

        File directory = new File(directoryPath);

        System.out.println("Directory path: " + directoryPath);
        System.out.println("Is Directory: " + directory.isDirectory());
        System.out.println("Exists: " + directory.exists());

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".gpx") || name.endsWith(".txt"));

            if (files != null) {
                for (File file : files) {
                    try {
                        byte[] fileData = Files.readAllBytes(file.toPath());
                        gpxService.saveFileToDB(file.getName(), fileData);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Failed to upload file: " + file.getName();
                    }
                }
                return "All files uploaded successfully!";
            } else {
                return "No GPX files found in directory.";
            }
        } else {
            return "Invalid directory path.";
        }
    }


}
