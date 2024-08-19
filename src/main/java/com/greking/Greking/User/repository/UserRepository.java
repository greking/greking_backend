package com.greking.Greking.User.repository;


import com.greking.Greking.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Jpa Repo CRUD 기능 활용
public interface UserRepository extends JpaRepository<User, Long> {

    //기존 회원 email 존재하는지 check
    Optional<User> findByEmail(String email);
}
