package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.angular.orders.dao.CustomerDao;
import com.himanshu.springboot2.angular.orders.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigurer {
  @Bean
  public CustomerService customerService(CustomerDao customerDao) {
    return new CustomerService(customerDao);
  }
}
