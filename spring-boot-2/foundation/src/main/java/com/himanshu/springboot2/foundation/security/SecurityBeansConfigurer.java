package com.himanshu.springboot2.foundation.security;

import com.himanshu.springboot2.foundation.security.dao.*;
import com.himanshu.springboot2.foundation.security.services.IUserService;
import com.himanshu.springboot2.foundation.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import javax.sql.DataSource;

@Configuration
public class SecurityBeansConfigurer {

  @Autowired
  private DataSource dataSource;

  @Bean
  public IUserDao userDao() {
    return new UserDao(dataSource);
  }

  @Bean
  public IRoleDao roleDao() {
    return new RoleDao(dataSource);
  }

  @Bean
  public IUserRoleMappingDao userRoleMappingDao() {
    return new UserRoleMappingDao(dataSource);
  }

  @Bean
  public IUserService userService() {
    return new UserService(userDao(), roleDao(), userRoleMappingDao());
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new AuthenticationProviderImpl(userService());
  }
}
