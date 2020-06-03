package com.corradowaver.accounting.dao;

import com.corradowaver.accounting.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
  Department findDepartmentById(Long id);
  Department findDepartmentByName(String name);
}
