package com.example.demo.duanthuctap.service;

import com.example.demo.duanthuctap.entity.TaskEntity;

import java.util.List;

public interface TaskService {

    TaskEntity create(TaskEntity task);

    List<TaskEntity> getAll();

    TaskEntity getById(Long id);

    void delete(Long id);
}
