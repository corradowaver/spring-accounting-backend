package com.corradowaver.accounting.auth;

import com.corradowaver.accounting.dao.EmployeeRepository;
import com.corradowaver.accounting.helpers.ClientAddress;
import com.corradowaver.accounting.model.Employee;
import com.corradowaver.accounting.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = ClientAddress.CLIENT_HOST)
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtProvider jwtProvider;

  //TODO make EmployeeRepo extends UserRepo
  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  PasswordEncoder encoder;

  @PostMapping("/signin")
  public Map<Object, Object> signIn(@RequestBody AuthRequest request) {
    try {
      String username = request.getUsername();
      Employee employee = employeeRepository.findEmployeeByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      String password = request.getPassword();
      if (!encoder.matches(password, employee.getPassword())) {
        throw new RuntimeException("Password doesn't match username");
      }

      String token = jwtProvider.createToken(
          username,
          employee.getAuthorities()
      );

      return Map.of("token", token,
          "details", employee);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

}
