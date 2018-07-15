package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Item;
import com.himanshu.springboot2.angular.orders.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDao extends CrudRepository<Order, Long> {
  @Query(value = "select o from Order o JOIN FETCH o.customer customer")
  List<Order> listAllViaJoinFetch();
}
