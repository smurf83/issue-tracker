package com.example.issuetracker.dto;

import com.example.issuetracker.model.Role;

public record UserResponse(
    Long id,
    String email,
    Role role
) {}