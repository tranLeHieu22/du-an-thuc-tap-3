package com.example.demo.duanthuctap.service.impl;

import com.example.demo.duanthuctap.entity.TaskEntity;
import com.example.demo.duanthuctap.exception.BusinessException;
import com.example.demo.duanthuctap.repository.TaskRepository;
import com.example.demo.duanthuctap.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity create(TaskEntity task) {

        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new BusinessException("Title is required");
        }

        return taskRepository.save(task);
    }

    @Override
    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity getById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Task not found: " + id));
    }

    @Override
    public void delete(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new BusinessException("Task not exist: " + id);
        }

        taskRepository.deleteById(id);
    }
}
