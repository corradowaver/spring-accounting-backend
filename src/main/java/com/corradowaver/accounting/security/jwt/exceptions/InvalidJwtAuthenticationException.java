package com.corradowaver.accounting.security.jwt.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException {
  public InvalidJwtAuthenticationException(String expired_or_invalid_token) {
  }
}
