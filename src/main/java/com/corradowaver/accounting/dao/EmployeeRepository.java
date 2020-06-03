package com.corradowaver.accounting.dao;

import com.corradowaver.accounting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Employee findEmployeeById(Long id);
  Optional<Employee> findEmployeeByUsername(String username);
}
