package com.example.demo.duanthuctap.service;

import com.example.demo.duanthuctap.dto.TaskRequest;
import com.example.demo.duanthuctap.dto.TaskResponse;
import com.example.demo.duanthuctap.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponse create(TaskRequest request);

    List<TaskResponse> getAll();

    TaskResponse getById(Long id);

    void delete(Long id);

    void assignTask(Long taskId, Long userId);

    void updateStatus(Long taskId, TaskStatus status);

    List<TaskResponse> getByProject(Long projectId);

    List<TaskResponse> getByUser(Long userId);
}