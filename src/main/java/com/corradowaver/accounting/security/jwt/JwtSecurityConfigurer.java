package com.corradowaver.accounting.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtProvider jwtProvider;

  public JwtSecurityConfigurer(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    JwtFilter filter = new JwtFilter(jwtProvider);
    builder.exceptionHandling()
        .authenticationEntryPoint(new JwtAuthEntryPoint())
        .and()
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
