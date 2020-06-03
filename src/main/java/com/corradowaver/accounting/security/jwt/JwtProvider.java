package com.corradowaver.accounting.security.jwt;

import com.corradowaver.accounting.security.jwt.exceptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtProvider {

  @Autowired
  private JwtProperties jwtProperties;

  @Qualifier("EmployeeService")
  @Autowired
  private UserDetailsService userDetailsService;

  private String secretKey;

  private final static String BEARER_PREFIX = "Bearer ";

  @PostConstruct
  private void init() {
    secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
  }

  public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("authorities", authorities);

    Date now = new Date();
    Date expires = new Date(now.getTime() + jwtProperties.getExpirationTimeInMs());

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expires)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch(JwtException | IllegalArgumentException e) {
      throw new InvalidJwtAuthenticationException("Expired or invalid token");
    }
  }

  public String resolveToken(HttpServletRequest request) {
    String header = request.getHeader("authorization");
    if (header != null && header.startsWith(BEARER_PREFIX)) {
      return header.substring(BEARER_PREFIX.length());
    }
    return null;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

}

