package com.example.demo.duanthuctap.controller;

import com.example.demo.duanthuctap.entity.TaskEntity;
import com.example.demo.duanthuctap.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create task
    @PostMapping
    public TaskEntity create(@RequestBody TaskEntity task) {
        return taskService.create(task);
    }

    // Get all
    @GetMapping
    public List<TaskEntity> getAll() {
        return taskService.getAll();
    }

    // Get by id
    @GetMapping("/{id}")
    public TaskEntity getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
