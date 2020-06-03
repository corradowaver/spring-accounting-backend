package com.corradowaver.accounting;

import com.corradowaver.accounting.dao.EmployeeRepository;
import com.corradowaver.accounting.model.Department;
import com.corradowaver.accounting.model.Employee;
import com.corradowaver.accounting.security.roles.ApplicationUserRole;
import com.corradowaver.accounting.service.department.DepartmentService;
import com.corradowaver.accounting.service.employee.EmployeeService;
import com.corradowaver.accounting.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CorradowaverApplication {
  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  EmployeeService employeeService;

  @Autowired
  PasswordEncoder pwdEncoder;

  public static void main(String[] args) {
    SpringApplication.run(CorradowaverApplication.class, args);
  }

//  @Bean
//  public CommandLineRunner test(EmployeeService employeeService, DepartmentService departmentService, ProjectService projectService) {
//    return args -> {
//
//      departmentService.addDepartment(new Department("lmao"));
//
//      var department = departmentService.getDepartmentByName("lmao");
//      employeeService.addEmployee(new Employee("Georgii", "Corradowaver", "DB",
//          "Admin", 3000,
//          department,
//          "admin",
//          pwdEncoder.encode("admin"),
//         ApplicationUserRole.ADMIN));
//    };
//  }
}
