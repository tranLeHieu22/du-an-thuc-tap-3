package com.example.demo.duanthuctap.controller;

import com.example.demo.duanthuctap.dto.TaskRequest;
import com.example.demo.duanthuctap.dto.TaskResponse;
import com.example.demo.duanthuctap.entity.TaskStatus;
import com.example.demo.duanthuctap.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public String assignTask(@PathVariable Long taskId,
                             @PathVariable Long userId) {
        taskService.assignTask(taskId, userId);
        return "Assign success";
    }

    @PutMapping("/{taskId}/status")
    public String updateStatus(@PathVariable Long taskId,
                               @RequestParam TaskStatus status) {
        taskService.updateStatus(taskId, status);
        return "Update status success";
    }

    @GetMapping("/project/{projectId}")
    public List<TaskResponse> getByProject(@PathVariable Long projectId) {
        return taskService.getByProject(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponse> getByUser(@PathVariable Long userId) {
        return taskService.getByUser(userId);
    }
}