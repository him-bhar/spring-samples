package com.himanshu.projects.springsecurity.minified.config;

import com.himanshu.projects.springsecurity.minified.web.controller.ControllerApiTaskFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by himanshu on 13-06-2017.
 */
@Configuration
public class DeferredApiSpringConfig {
  @Bean
  public ControllerApiTaskFactory taskFactory() {
    ControllerApiTaskFactory taskFactory = new ControllerApiTaskFactory();
    return taskFactory;
  }
}
