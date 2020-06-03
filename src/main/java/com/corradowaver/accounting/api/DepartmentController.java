package com.corradowaver.accounting.api;

import com.corradowaver.accounting.helpers.ClientAddress;
import com.corradowaver.accounting.model.Department;
import com.corradowaver.accounting.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = ClientAddress.CLIENT_HOST)
@RequestMapping("api/departments")
@RestController
public class DepartmentController {
  private final DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void addDepartment(@Valid @NonNull @RequestBody Department department) {
    try {
      departmentService.addDepartment(department);
    } catch (Exception e) {
      throw new RuntimeException("Department with same name already exists");
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Department> getAllDepartments() {
    try {
      return departmentService.getDepartments();
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

  @GetMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Department getDepartmentById(@PathVariable("id") long id) {
    try {
      return departmentService.getDepartmentById(id);
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

  @DeleteMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void deleteDepartment(@PathVariable("id") long id) {
    try {
      departmentService.deleteDepartmentById(id);
    } catch (Exception e) {
      throw new RuntimeException("You cannot delete a department while projects are attached to it \n" +
          "Try to detach projects first.");
    }
  }

  @PutMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void updateDepartment(@PathVariable("id") long id,
                               @Valid @NonNull @RequestBody Department department) {
    try {
      departmentService.updateDepartmentById(id, department);
    } catch (Exception e) {
      throw new RuntimeException("Saving failed");
    }
  }
}
