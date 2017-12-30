package com.himanshu.poc.spring.samples.springbootwebreactive.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.himanshu.poc.spring.samples.springbootwebreactive"})
public class ConfigLoader {
  @Value("${my.prop.msg}")
  private String message;

  @Bean(name="messageBean")
  public String message() {
    return message;
  }

}
