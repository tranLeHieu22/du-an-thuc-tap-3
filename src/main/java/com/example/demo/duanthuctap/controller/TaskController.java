package com.example.demo.duanthuctap.controller;

import com.example.demo.duanthuctap.entity.TaskEntity;
import com.example.demo.duanthuctap.entity.TaskStatus;
import com.example.demo.duanthuctap.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // MANAGER ONLY
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public TaskEntity create(@Valid @RequestBody TaskEntity task) {
        return taskService.create(task);
    }

    @GetMapping
    public List<TaskEntity> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskEntity getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String assignTask(@PathVariable Long taskId,
                             @PathVariable Long userId) {
        taskService.assignTask(taskId, userId);
        return "Assign success";
    }

    // USER được update status
    @PutMapping("/{taskId}/status")
    public String updateStatus(@PathVariable Long taskId,
                               @RequestParam TaskStatus status) {
        taskService.updateStatus(taskId, status);
        return "Update status success";
    }

    @GetMapping("/project/{projectId}")
    public List<TaskEntity> getByProject(@PathVariable Long projectId) {
        return taskService.getByProject(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskEntity> getByUser(@PathVariable Long userId) {
        return taskService.getByUser(userId);
    }
}