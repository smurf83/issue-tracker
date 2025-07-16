package com.example.issuetracker.controller;

import com.example.issuetracker.dto.CreateIssueRequest;
import com.example.issuetracker.dto.IssueResponse;
import com.example.issuetracker.dto.UpdateIssueRequest;
import com.example.issuetracker.model.Priority;
import com.example.issuetracker.model.Status;
import com.example.issuetracker.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Issue Management", description = "Issue management APIs")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @Operation(summary = "Create a new issue (TESTER, ADMIN only)")
    @PreAuthorize("hasRole('TESTER') or hasRole('ADMIN')")
    public ResponseEntity<IssueResponse> createIssue(@Valid @RequestBody CreateIssueRequest request) {
        IssueResponse response = issueService.createIssue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all issues with optional filtering and pagination")
    public ResponseEntity<Page<IssueResponse>> getAllIssues(
            @Parameter(description = "Filter by status")
            @RequestParam(required = false) Status status,
            
            @Parameter(description = "Filter by priority")
            @RequestParam(required = false) Priority priority,
            
            @Parameter(description = "Filter by assigned user ID")
            @RequestParam(required = false) Long assignedToId,
            
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Sort by field")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            
            @Parameter(description = "Sort direction")
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<IssueResponse> issues = issueService.getAllIssues(
                status, priority, assignedToId, pageable
        );
        
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by ID")
    public ResponseEntity<IssueResponse> getIssueById(@PathVariable Long id) {
        IssueResponse response = issueService.getIssueById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update issue (ADMIN, DEVELOPER can change status/assignee)")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEVELOPER')")
    public ResponseEntity<IssueResponse> updateIssue(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIssueRequest request
    ) {
        IssueResponse response = issueService.updateIssue(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete issue (ADMIN only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}