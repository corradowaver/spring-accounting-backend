package com.corradowaver.accounting.dao;

import com.corradowaver.accounting.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  Project findProjectById(Long id);
}
