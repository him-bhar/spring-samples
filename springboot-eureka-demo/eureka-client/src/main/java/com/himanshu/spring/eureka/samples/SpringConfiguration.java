package com.himanshu.spring.eureka.samples;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

  @Bean
  public HealthIndicator customClientHealthIndicator() {
    return new ClientHealthIndicator();
  }
}
