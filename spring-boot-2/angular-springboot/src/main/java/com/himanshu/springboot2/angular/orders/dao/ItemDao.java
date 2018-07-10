package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {
}
