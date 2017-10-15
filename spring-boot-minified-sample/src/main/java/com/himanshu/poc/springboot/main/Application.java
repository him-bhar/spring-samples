package com.himanshu.poc.springboot.main;

import com.himanshu.poc.springboot.config.SecurityConfig;
import com.himanshu.poc.springboot.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.himanshu.poc.springboot.web.controller", "com.himanshu.poc.springboot.security"})
@Import(value = {SwaggerConfig.class, SecurityConfig.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}