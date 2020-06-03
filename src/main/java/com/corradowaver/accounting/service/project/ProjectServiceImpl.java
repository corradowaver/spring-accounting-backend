package com.corradowaver.accounting.service.project;

import com.corradowaver.accounting.dao.ProjectRepository;
import com.corradowaver.accounting.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public int addProject(Project project) {
    projectRepository.save(project);
    return 0;
  }

  @Override
  public int deleteProjectById(long id) {
    projectRepository.deleteById(id);
    return 0;
  }

  @Override
  public List<Project> getProjects() {
    return projectRepository.findAll();
  }

  @Override
  public Project getProjectById(Long id) {
    return projectRepository.findProjectById(id);
  }

  @Override
  public int updateProjectById(Long id, Project project) {
    project.setId(id);
    projectRepository.save(project);
    return 0;
  }
}
