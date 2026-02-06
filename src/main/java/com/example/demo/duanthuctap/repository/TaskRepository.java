package com.example.demo.duanthuctap.repository;

import com.example.demo.duanthuctap.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
