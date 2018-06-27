package com.himanshu.springboot2.angular.orders.service;

import com.himanshu.springboot2.angular.orders.dao.CustomerDao;
import com.himanshu.springboot2.angular.orders.dao.ICustomerDaoNonData;
import com.himanshu.springboot2.angular.orders.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

public class CustomerService {
  private final CustomerDao customerDao;
  private final ICustomerDaoNonData customerDaoNonData;

  public CustomerService(CustomerDao customerDao, ICustomerDaoNonData customerDaoNonData) {
    this.customerDao = customerDao;
    this.customerDaoNonData = customerDaoNonData;
  }

  public Iterable<Customer> listAll() {
    return this.customerDao.findAll();
  }

  public Customer getCustomer() {
    return this.customerDaoNonData.loadCustomerById(1l);
  }

  @Transactional(transactionManager="sampleDSTransactionManager")
  public Customer getAndUpdateCustomer() {
    Customer c = this.customerDaoNonData.loadCustomerById(1l);
    c.setCountry("India");
    customerDaoNonData.updateCustomer(c);
    Customer customer = customerDaoNonData.loadCustomerById(1);
    return customer;

  }

}
