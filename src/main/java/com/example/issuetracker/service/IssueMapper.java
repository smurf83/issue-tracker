package com.example.issuetracker.service;

import com.example.issuetracker.dto.IssueResponse;
import com.example.issuetracker.dto.UserResponse;
import com.example.issuetracker.model.Issue;
import com.example.issuetracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public IssueResponse toResponse(Issue issue) {
        return new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getPriority(),
                toUserResponse(issue.getCreatedBy()),
                issue.getAssignedTo() != null ? toUserResponse(issue.getAssignedTo()) : null,
                issue.getCreatedAt(),
                issue.getUpdatedAt()
        );
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}