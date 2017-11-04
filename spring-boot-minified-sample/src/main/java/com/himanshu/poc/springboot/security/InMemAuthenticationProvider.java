package com.himanshu.poc.springboot.security;

import com.google.common.collect.Lists;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class InMemAuthenticationProvider implements AuthenticationProvider {
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    System.out.println("Attempting to login");
    if (((String) authentication.getPrincipal()).equals("himanshu") && ((String) authentication.getCredentials()).equals("bhardwaj")) {
      System.out.println("Login successful");
      return new UsernamePasswordAuthenticationToken(
              (String) authentication.getPrincipal(), null, Lists.newArrayList(new SimpleGrantedAuthority("USER_ROLE"))
      );
    } else {
      System.out.println("Login failed");
      return null;
    }
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
