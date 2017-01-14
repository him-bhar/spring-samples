package com.himanshu.poc.spring.concepts.config.loader;

import org.springframework.context.annotation.Bean;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.himanshu.poc.spring.concepts.manager.DummyHealthCheckCandidate;
import com.himanshu.poc.spring.concepts.manager.QueueManager;

public class SpringHealthCheckConfigurer {
  
  @Bean
  public MetricRegistry metricRegistry() {
    return new MetricRegistry();
  }
  
  @Bean
  public QueueManager queueManager(MetricRegistry metricRegistry) {
    return new QueueManager(metricRegistry, "testname");
  }
  
  @Bean
  public HealthCheckRegistry healthCheckRegistry() {
    return new HealthCheckRegistry();
  }
  
  @Bean
  public DummyHealthCheckCandidate healthCheckCandidate() {
    return new DummyHealthCheckCandidate();
  }

}
