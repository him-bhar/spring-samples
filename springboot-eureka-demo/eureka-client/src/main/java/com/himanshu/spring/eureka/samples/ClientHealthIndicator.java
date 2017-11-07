package com.himanshu.spring.eureka.samples;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

public class ClientHealthIndicator extends AbstractHealthIndicator {
  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
    int errorCode = check();
    if (errorCode != 1) {
      builder.withDetail("Health check failed", errorCode);
    } else {
      builder.up();
    }
  }

  private int check() {
    return 0;
  }
}
