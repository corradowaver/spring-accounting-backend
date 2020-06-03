package com.corradowaver.accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "department_id")
  private long id;

  @Column(name = "name", unique = true)
  private String name;

  @JsonIgnoreProperties("department")
  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "departments_employees", joinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "department_id")},
      inverseJoinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")})
  private List<Employee> employees = new ArrayList<>();

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public void removeEmployee(Employee employee) {
    getEmployees().remove(employee);
    employee.setDepartment(null);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }


}
