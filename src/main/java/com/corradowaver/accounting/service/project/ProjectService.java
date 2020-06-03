package com.corradowaver.accounting.service.project;

import com.corradowaver.accounting.model.Project;

import java.util.List;

public interface ProjectService {

  int addProject(Project matching);

  int deleteProjectById(long id);

  List<Project> getProjects();

  Project getProjectById(Long id);

  int updateProjectById(Long id, Project project);

}
