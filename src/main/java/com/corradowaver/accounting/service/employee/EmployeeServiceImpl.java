package com.corradowaver.accounting.service.employee;

import com.corradowaver.accounting.dao.EmployeeRepository;
import com.corradowaver.accounting.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Qualifier("EmployeeService")
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return employeeRepository.findEmployeeByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
  }

  @Override
  public int addEmployee(Employee employee) {
    employeeRepository.save(employee);
    return 0;
  }

  @Override
  public List<Employee> getEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee getEmployeeById(Long id) {
    return employeeRepository.findEmployeeById(id);
  }

  @Override
  public int updateEmployeeById(Long id, Employee employee) {
    employee.setId(id);
    employeeRepository.save(employee);
    return 0;
  }

  @Override
  public int deleteEmployeeById(Long id) {
    employeeRepository.deleteById(id);
    return 0;
  }

}
