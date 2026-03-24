package com.example.demo.duanthuctap.dto;

import com.example.demo.duanthuctap.entity.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;

    private String username;
    private String projectName;
}