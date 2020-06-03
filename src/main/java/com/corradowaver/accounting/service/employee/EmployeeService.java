package com.corradowaver.accounting.service.employee;

import com.corradowaver.accounting.model.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Qualifier("EmployeeService")
public interface EmployeeService extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  int addEmployee(Employee employee);

  List<Employee> getEmployees();

  Employee getEmployeeById(Long id);

  int updateEmployeeById(Long id, Employee employee);

  int deleteEmployeeById(Long id);
}
