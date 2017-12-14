package com.himanshu.spring.eureka.another;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.himanshu.spring.eureka.another"})
//@EnableEurekaClient
@EnableFeignClients
public class FeignEurekaClientApplication {
  public static void main(String[] args) {
    new SpringApplication(FeignEurekaClientApplication.class).run(args);
  }
}
