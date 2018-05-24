package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer, Long> {
}
