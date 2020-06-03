package com.corradowaver.accounting.security;

import com.corradowaver.accounting.helpers.ClientAddress;
import com.corradowaver.accounting.security.jwt.JwtProvider;
import com.corradowaver.accounting.security.jwt.JwtSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  @Autowired
  public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
    this.passwordEncoder = passwordEncoder;
    this.jwtProvider = jwtProvider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/auth/signin").permitAll()
        .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
        .anyRequest().authenticated()
        .and()
        .apply(new JwtSecurityConfigurer(jwtProvider));

  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList(ClientAddress.CLIENT_HOST));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
