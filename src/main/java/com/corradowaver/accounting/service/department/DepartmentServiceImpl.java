package com.corradowaver.accounting.service.department;

import com.corradowaver.accounting.dao.DepartmentRepository;
import com.corradowaver.accounting.model.Department;
import com.corradowaver.accounting.model.Employee;
import com.corradowaver.accounting.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private DepartmentRepository departmentRepository;

  @Autowired
  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public int addDepartment(Department department) {
    departmentRepository.save(department);
    return 0;
  }

  @Override
  public List<Department> getDepartments() {
    return departmentRepository.findAll();
  }

  @Override
  public Department getDepartmentById(Long id) {
    return departmentRepository.findDepartmentById(id);
  }

  @Override
  public Department getDepartmentByName(String name) {
    return departmentRepository.findDepartmentByName(name);
  }

  @Override
  public int updateDepartmentById(Long id, Department department) {
    Department oldDepartment = departmentRepository.findDepartmentById(id);
    department.setId(id);
    department.setEmployees(oldDepartment.getEmployees());
    departmentRepository.save(department);
    return 0;
  }

  @Override
  public int deleteDepartmentById(Long id) {
    departmentRepository.deleteById(id);
    return 0;
  }

}
