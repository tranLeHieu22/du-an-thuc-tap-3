package com.example.demo.duanthuctap.repository;

import com.example.demo.duanthuctap.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
