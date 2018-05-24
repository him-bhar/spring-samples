package com.himanshu.springboot2.angular.orders.service;

import com.himanshu.springboot2.angular.orders.dao.CustomerDao;
import com.himanshu.springboot2.angular.orders.entity.Customer;

public class CustomerService {
  private final CustomerDao customerDao;

  public CustomerService(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Iterable<Customer> listAll() {
    return this.customerDao.findAll();
  }

}
