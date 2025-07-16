package com.example.issuetracker.dto;

import com.example.issuetracker.model.Role;

public record AuthResponse(
    String token,
    String email,
    Role role,
    String message
) {}