package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, Long> {

}
