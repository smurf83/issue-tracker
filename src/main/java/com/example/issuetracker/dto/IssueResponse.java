package com.example.issuetracker.dto;

import com.example.issuetracker.model.Priority;
import com.example.issuetracker.model.Status;

import java.time.LocalDateTime;

public record IssueResponse(
    Long id,
    String title,
    String description,
    Status status,
    Priority priority,
    UserResponse createdBy,
    UserResponse assignedTo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}