package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Customer;

public interface ICustomerDaoNonData {
  Customer loadCustomerById(long id);
  void updateCustomer(Customer customer);
}
