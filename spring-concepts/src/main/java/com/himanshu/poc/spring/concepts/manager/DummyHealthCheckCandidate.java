package com.himanshu.poc.spring.concepts.manager;

import com.codahale.metrics.health.HealthCheck;

public class DummyHealthCheckCandidate extends HealthCheck {
  
  private int i = 0;

  @Override
  protected HealthCheck.Result check() throws Exception {
    i++;
    HealthCheck.Result result = null;
    if (i < 3 || i > 6) {
      result = HealthCheck.Result.healthy("DummyHealthCheckCandidate is fine");
    } else {
      result = HealthCheck.Result.unhealthy("DummyHealthCheckCandidate is not working.");
    }
    return result;
  }

}
