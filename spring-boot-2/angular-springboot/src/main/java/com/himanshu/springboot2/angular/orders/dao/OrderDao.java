package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Long> {
}
