package com.corradowaver.accounting.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String secretKey = "hardcodedSecretKey";
  private long expirationTimeInMs = 1800000;

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public long getExpirationTimeInMs() {
    return expirationTimeInMs;
  }

  public void setExpirationTimeInMs(long expirationTimeInMs) {
    this.expirationTimeInMs = expirationTimeInMs;
  }
}
