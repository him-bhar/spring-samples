package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.foundation.security.BaseSecurityConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration extends BaseSecurityConfigurer {
  public SecurityConfiguration() {
    super("(.*?)");
  }
}
