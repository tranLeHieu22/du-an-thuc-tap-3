package com.example.demo.duanthuctap.service;

import com.example.demo.duanthuctap.entity.TaskEntity;
import com.example.demo.duanthuctap.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskEntity create(TaskEntity task);

    List<TaskEntity> getAll();

    TaskEntity getById(Long id);

    void delete(Long id);

    void assignTask(Long taskId, Long userId);

    void updateStatus(Long taskId, TaskStatus status);

    List<TaskEntity> getByProject(Long projectId);

    List<TaskEntity> getByUser(Long userId);
}