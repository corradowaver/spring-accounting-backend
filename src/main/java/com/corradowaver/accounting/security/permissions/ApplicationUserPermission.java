package com.corradowaver.accounting.security.permissions;

public enum ApplicationUserPermission {
  EMPLOYEE_READ("employee:read"),
  EMPLOYEE_WRITE("employee:write"),
  DEPARTMENT_READ("department:read"),
  DEPARTMENT_WRITE("department:write"),
  PROJECT_READ("project:read"),
  PROJECT_WRITE("project:write");

  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
