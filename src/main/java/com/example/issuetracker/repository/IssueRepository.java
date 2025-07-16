package com.example.issuetracker.repository;

import com.example.issuetracker.model.Issue;
import com.example.issuetracker.model.Priority;
import com.example.issuetracker.model.Status;
import com.example.issuetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    @Query("SELECT i FROM Issue i WHERE " +
           "(:status IS NULL OR i.status = :status) AND " +
           "(:priority IS NULL OR i.priority = :priority) AND " +
           "(:assignedTo IS NULL OR i.assignedTo = :assignedTo)")
    Page<Issue> findIssuesWithFilters(
        @Param("status") Status status,
        @Param("priority") Priority priority,
        @Param("assignedTo") User assignedTo,
        Pageable pageable
    );
}