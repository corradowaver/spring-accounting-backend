package com.corradowaver.accounting.model;

import com.corradowaver.accounting.security.roles.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employees")
public class Employee implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employee_id")
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "father_name")
  private String fatherName;

  @Column(name = "position")
  private String position;

  @Column(name = "salary")
  private int salary;

  @JsonIgnoreProperties("employees")
  @ManyToOne
  @JoinTable(name = "departments_employees",
      joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")},
      inverseJoinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "department_id")})
  private Department department;

  @Column(name = "login", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "granted_authorities")
  private ApplicationUserRole applicationUserRole;

  public Employee() {
  }

  public Employee(String firstName, String lastName, String fatherName,
                  String position, int salary, Department department,
                  String username, String password, ApplicationUserRole role
                  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.fatherName = fatherName;
    this.position = position;
    this.salary = salary;
    this.department = department;
    this.username = username;
    this.password = password;
    this.applicationUserRole = role;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFatherName() {
    return fatherName;
  }

  public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public Department getDepartment() {
    return department;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", fatherName='" + fatherName + '\'' +
        ", position='" + position + '\'' +
        ", salary=" + salary +
        ", department=" + department +
        '}';
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return applicationUserRole.getGrantedAuthorities();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setApplicationUserRole(ApplicationUserRole applicationUserRole) {
    this.applicationUserRole = applicationUserRole;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
