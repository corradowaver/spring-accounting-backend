package com.corradowaver.accounting.api;

import com.corradowaver.accounting.helpers.ClientAddress;
import com.corradowaver.accounting.model.Employee;
import com.corradowaver.accounting.security.roles.ApplicationUserRole;
import com.corradowaver.accounting.service.department.DepartmentService;
import com.corradowaver.accounting.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = ClientAddress.CLIENT_HOST)
@RequestMapping("/api/employees")
@RestController
public class EmployeeController {
  private final EmployeeService employeeService;
  private final DepartmentService departmentService;
  private final PasswordEncoder encoder;

  @Autowired
  public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, PasswordEncoder encoder) {
    this.employeeService = employeeService;
    this.departmentService = departmentService;
    this.encoder = encoder;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void addEmployee(@Valid @NonNull @RequestBody Employee employee) {
    var password = employee.getPassword();
    var encoded = encoder.encode(password);
    employee.setPassword(encoded);
    var role = ApplicationUserRole.EMPLOYEE;
    employee.setApplicationUserRole(role);
    try {
      String departmentName = employee.getDepartment().getName();
      var department = departmentService.getDepartmentByName(departmentName);
      employee.setDepartment(department);
    } catch (Exception e) {
      throw new RuntimeException("User with same username already exists!");
    } finally {
      employeeService.addEmployee(employee);
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Employee> getAllEmployees() {
    try {
      return employeeService.getEmployees();
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

  @GetMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Employee getEmployeeById(@PathVariable("id") long id) {
    try {
      return employeeService.getEmployeeById(id);
    } catch (Exception e) {
      throw new RuntimeException("Loading data failed");
    }
  }

  @DeleteMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void deleteEmployee(@PathVariable("id") long id) {
    try {
      employeeService.deleteEmployeeById(id);
    } catch (Exception e) {
      throw new RuntimeException("Saving data failed");
    }
  }

  @PutMapping(path = "{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void updateEmployee(@PathVariable("id") long id,
                             @Valid @NonNull @RequestBody Employee employee) {
    //only EMPLOYEE has 1 permission
    var role = employeeService.getEmployeeById(id).getAuthorities().size() == 1
        ? ApplicationUserRole.EMPLOYEE
        : ApplicationUserRole.ADMIN;
    employee.setApplicationUserRole(role);
    try {
      String departmentName = employee.getDepartment().getName();
      var department = departmentService.getDepartmentByName(departmentName);
      employee.setDepartment(department);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    } finally {
      employeeService.updateEmployeeById(id, employee);
    }
  }
}
