package com.corradowaver.accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "project_id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "cost")
  private int cost;

  @JsonIgnoreProperties("employees")
  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @Column(name = "date_beg")
  private String dateBegin;

  @Column(name = "date_end")
  private String dateEnd;

  @Column(name = "date_end_real")
  private String dateEndReal;

  public Project() {
  }

  public Project(String name, int cost, String dateBegin, String dateEnd, String dateEndReal) {
    this.name = name;
    this.cost = cost;
    this.dateBegin = dateBegin;
    this.dateEnd = dateEnd;
    this.dateEndReal = dateEndReal;
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

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getDateBegin() {
    return dateBegin;
  }

  public void setDateBegin(String dateBegin) {
    this.dateBegin = dateBegin;
  }

  public String getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
  }

  public String getDateEndReal() {
    return dateEndReal;
  }

  public void setDateEndReal(String dateEndReal) {
    this.dateEndReal = dateEndReal;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

}
