package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
}