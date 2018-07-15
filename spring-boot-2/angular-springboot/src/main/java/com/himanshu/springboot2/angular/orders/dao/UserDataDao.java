package com.himanshu.springboot2.angular.orders.dao;

import com.himanshu.springboot2.angular.orders.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDataDao extends CrudRepository<User, Integer> {
}
