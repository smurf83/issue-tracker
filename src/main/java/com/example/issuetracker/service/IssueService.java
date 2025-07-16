package com.example.issuetracker.service;

import com.example.issuetracker.dto.CreateIssueRequest;
import com.example.issuetracker.dto.IssueResponse;
import com.example.issuetracker.dto.UpdateIssueRequest;
import com.example.issuetracker.model.Issue;
import com.example.issuetracker.model.Priority;
import com.example.issuetracker.model.Status;
import com.example.issuetracker.model.User;
import com.example.issuetracker.repository.IssueRepository;
import com.example.issuetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final IssueMapper issueMapper;

    public IssueResponse createIssue(CreateIssueRequest request) {
        User currentUser = getCurrentUser();

        Issue issue = Issue.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .status(Status.OPEN)
                .createdBy(currentUser)
                .build();

        Issue savedIssue = issueRepository.save(issue);
        return issueMapper.toResponse(savedIssue);
    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> getAllIssues(
            Status status,
            Priority priority,
            Long assignedToId,
            Pageable pageable
    ) {
        User assignedTo = null;
        if (assignedToId != null) {
            assignedTo = userRepository.findById(assignedToId)
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        Page<Issue> issues = issueRepository.findIssuesWithFilters(
                status, priority, assignedTo, pageable
        );

        return issues.map(issueMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public IssueResponse getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        return issueMapper.toResponse(issue);
    }

    public IssueResponse updateIssue(Long id, UpdateIssueRequest request) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (request.title() != null) {
            issue.setTitle(request.title());
        }
        if (request.description() != null) {
            issue.setDescription(request.description());
        }
        if (request.status() != null) {
            issue.setStatus(request.status());
        }
        if (request.priority() != null) {
            issue.setPriority(request.priority());
        }
        if (request.assignedToId() != null) {
            User assignedTo = userRepository.findById(request.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            issue.setAssignedTo(assignedTo);
        }

        Issue updatedIssue = issueRepository.save(issue);
        return issueMapper.toResponse(updatedIssue);
    }

    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new RuntimeException("Issue not found");
        }
        issueRepository.deleteById(id);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
}