package com.corradowaver.accounting.security.roles;

import com.corradowaver.accounting.security.permissions.ApplicationUserPermission;
import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.corradowaver.accounting.security.permissions.ApplicationUserPermission.*;

public enum ApplicationUserRole {
  EMPLOYEE(Sets.newHashSet()),
  ADMIN(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE,
      DEPARTMENT_READ, DEPARTMENT_WRITE, PROJECT_READ, PROJECT_WRITE));

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
