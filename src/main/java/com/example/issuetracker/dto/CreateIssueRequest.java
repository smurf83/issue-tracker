package com.example.issuetracker.dto;

import com.example.issuetracker.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIssueRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    String title,
    
    String description,
    
    @NotNull(message = "Priority is required")
    Priority priority
) {}