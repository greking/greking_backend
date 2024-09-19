package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.GPXFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GPXRepository extends JpaRepository<GPXFile, Long> {
    Optional<GPXFile> findByCourseName(String courseName);
}
