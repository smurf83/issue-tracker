package com.example.issuetracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    String message,
    List<String> details,
    LocalDateTime timestamp,
    String path
) {}