package com.himanshu.springboot2.actuator.config;

import com.himanshu.springboot2.foundation.security.BaseSecurityConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration extends BaseSecurityConfigurer {
  public SecurityConfiguration() {
    super("(.*?)");
  }
}
