package com.example.demo.duanthuctap.repository;

import com.example.demo.duanthuctap.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}