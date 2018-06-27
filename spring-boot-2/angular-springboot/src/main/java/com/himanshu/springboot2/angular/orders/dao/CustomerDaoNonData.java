package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

public class CustomerDaoNonData implements ICustomerDaoNonData {

  @PersistenceContext(unitName = "orderPU")
  private EntityManager em;

  public Customer loadCustomerById(long id) {
    try {
      return em.find(Customer.class, id, LockModeType.PESSIMISTIC_WRITE);
    } finally {
    }
  }

  public void updateCustomer(Customer customer) {
    try {
      em.merge(customer);
      em.flush();
    } finally {
    }
  }
}
