package com.example.demo.duanthuctap.service.impl;

import com.example.demo.duanthuctap.dto.TaskRequest;
import com.example.demo.duanthuctap.dto.TaskResponse;
import com.example.demo.duanthuctap.entity.*;
import com.example.demo.duanthuctap.exception.BusinessException;
import com.example.demo.duanthuctap.repository.ProjectRepository;
import com.example.demo.duanthuctap.repository.TaskRepository;
import com.example.demo.duanthuctap.repository.UserRepository;
import com.example.demo.duanthuctap.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskResponse create(TaskRequest request) {

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new BusinessException("Title is required");
        }

        ProjectEntity project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BusinessException("Project not found"));

        if (request.getDeadline() != null &&
                request.getDeadline().isBefore(LocalDate.now())) {
            throw new BusinessException("Deadline phải >= ngày hiện tại");
        }

        TaskEntity task = new TaskEntity();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Task not found"));

        return mapToResponse(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void assignTask(Long taskId, Long userId) {

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Task not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public void updateStatus(Long taskId, TaskStatus status) {

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Task not found"));

        if (task.getStatus() == TaskStatus.DONE && status == TaskStatus.TODO) {
            throw new BusinessException("Không thể DONE -> TODO");
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public List<TaskResponse> getByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getByUser(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ================= MAP =================
    private TaskResponse mapToResponse(TaskEntity task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .username(task.getUser() != null ? task.getUser().getUsername() : null)
                .projectName(task.getProject() != null ? task.getProject().getName() : null)
                .build();
    }
}