package com.himanshu.spring.eureka.another;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.himanshu.spring.eureka.another"})
@EnableEurekaClient
public class AnotherEurekaClientApplication {
  public static void main(String[] args) {
    new SpringApplication(AnotherEurekaClientApplication.class).run(args);
  }
}
