package com.himanshu.springboot2.angular.orders.service;

import com.himanshu.springboot2.angular.orders.dao.UserDataDao;
import com.himanshu.springboot2.angular.orders.entity.User;
import org.springframework.transaction.annotation.Transactional;

public class UserRoleService {
  private final UserDataDao userDataDao;

  public UserRoleService(UserDataDao userDataDao) {
    this.userDataDao = userDataDao;
  }

  @Transactional(transactionManager = "sampleDSTransactionManager")
  public Iterable<User> getUsers() {
    return userDataDao.findAll();
  }
}
