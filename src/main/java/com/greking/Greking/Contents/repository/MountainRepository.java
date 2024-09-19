package com.greking.Greking.Contents.repository;

import com.greking.Greking.Contents.domain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, Long> {

    List<Mountain> findByAddressState(String addressState);

    Mountain findByName(String name);
}

