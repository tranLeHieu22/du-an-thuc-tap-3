package com.example.demo.duanthuctap.service.impl;

import com.example.demo.duanthuctap.entity.*;
import com.example.demo.duanthuctap.exception.BusinessException;
import com.example.demo.duanthuctap.repository.TaskRepository;
import com.example.demo.duanthuctap.repository.UserRepository;
import com.example.demo.duanthuctap.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskEntity create(TaskEntity task) {

        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new BusinessException("Title is required");
        }

        if (task.getProject() == null || task.getProject().getId() == null) {
            throw new BusinessException("Project is required");
        }

        if (task.getDeadline() != null &&
                task.getDeadline().isBefore(LocalDate.now())) {
            throw new BusinessException("Deadline phải >= ngày hiện tại");
        }

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
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

    // ================= ASSIGN TASK =================
    @Override
    public void assignTask(Long taskId, Long userId) {

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Task not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        // RULE: phải cùng project
        if (task.getProject() == null) {
            throw new BusinessException("Task chưa có project");
        }

        task.setUser(user);
        taskRepository.save(task);
    }

    // ================= UPDATE STATUS =================
    @Override
    public void updateStatus(Long taskId, TaskStatus status) {

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Task not found"));

        TaskStatus current = task.getStatus();

        // RULE: DONE không quay lại TODO
        if (current == TaskStatus.DONE && status == TaskStatus.TODO) {
            throw new BusinessException("Không thể chuyển DONE -> TODO");
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    // ================= QUERY =================
    @Override
    public List<TaskEntity> getByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<TaskEntity> getByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}