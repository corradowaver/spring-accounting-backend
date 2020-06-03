package com.corradowaver.accounting.api;

import com.corradowaver.accounting.helpers.ClientAddress;
import com.corradowaver.accounting.model.Project;
import com.corradowaver.accounting.service.department.DepartmentService;
import com.corradowaver.accounting.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = ClientAddress.CLIENT_HOST)
@RequestMapping("/api/projects")
@RestController
public class ProjectController {

  private final ProjectService projectService;
  private final DepartmentService departmentService;

  @Autowired
  public ProjectController(ProjectService projectService, DepartmentService departmentService) {
    this.projectService = projectService;
    this.departmentService = departmentService;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void addProject(@Valid @NonNull @RequestBody Project project) {
    try {
      String departmentName = project.getDepartment().getName();
      var department = departmentService.getDepartmentByName(departmentName);
      project.setDepartment(department);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    } finally {
      projectService.addProject(project);
    }
  }

  @DeleteMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void deleteProject(@PathVariable("id") long id) {
    try {
      projectService.deleteProjectById(id);
    } catch (Exception e) {
      throw new RuntimeException("Saving data failed");
    }
  }

  @PutMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void updateProject(@PathVariable("id") long id,
                            @Valid @NonNull @RequestBody Project project) {
    try {
      String departmentName = project.getDepartment().getName();
      var department = departmentService.getDepartmentByName(departmentName);
      project.setDepartment(department);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    } finally {
      projectService.updateProjectById(id, project);
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Project> getAllProjects() {
    try {
      return projectService.getProjects();
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

  @GetMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Project getProjectById(@PathVariable("id") long id) {
    try {
      return projectService.getProjectById(id);
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

}
