package com.example.issuetracker.dto;

import com.example.issuetracker.model.Priority;
import com.example.issuetracker.model.Status;
import jakarta.validation.constraints.Size;

public record UpdateIssueRequest(
    @Size(max = 255, message = "Title must not exceed 255 characters")
    String title,
    
    String description,
    
    Status status,
    
    Priority priority,
    
    Long assignedToId
) {}