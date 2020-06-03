package com.corradowaver.accounting.service.department;

import com.corradowaver.accounting.model.Department;

import java.util.List;

public interface DepartmentService {

  int addDepartment(Department department);

  List<Department> getDepartments();

  Department getDepartmentById(Long id);

  Department getDepartmentByName(String name);

  int updateDepartmentById(Long id, Department department);

  int deleteDepartmentById(Long id);

}
