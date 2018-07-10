package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.angular.orders.dao.*;
import com.himanshu.springboot2.angular.orders.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class OrderConfigurer {
  @Bean
  public CustomerService customerService(CustomerDao customerDao, ICustomerDaoNonData customerDaoNonData,
                                         ItemDao itemDao, OrderDao orderDao) {
    return new CustomerService(customerDao, customerDaoNonData, itemDao, orderDao);
  }

  @Bean
  public ICustomerDaoNonData customerDaoNonData(@Qualifier("orderEntityManagerFactory")EntityManagerFactory emf) {
    return new CustomerDaoNonData();
  }
}
