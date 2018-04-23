package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.foundation.context.PropertiesPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration extends PropertiesPostProcessor {
  public PropertiesConfiguration() {
    super("angular-demo");
  }
}
