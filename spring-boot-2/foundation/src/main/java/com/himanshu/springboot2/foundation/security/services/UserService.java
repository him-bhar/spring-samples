/*
 * Copyright 2014 himanshu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.himanshu.springboot2.foundation.security.services;

import com.himanshu.springboot2.foundation.security.dao.IRoleDao;
import com.himanshu.springboot2.foundation.security.dao.IUserDao;
import com.himanshu.springboot2.foundation.security.dao.IUserRoleMappingDao;
import com.himanshu.springboot2.foundation.security.domain.Role;
import com.himanshu.springboot2.foundation.security.domain.RoleEnum;
import com.himanshu.springboot2.foundation.security.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class UserService.
 */
public class UserService implements IUserService {
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  /** The user dao. */
  private final IUserDao userDao;
  
  /** The role dao. */
  private final IRoleDao roleDao;
  
  /** The user role mapping dao. */
  private final IUserRoleMappingDao userRoleMappingDao;

  public UserService(IUserDao userDao, IRoleDao roleDao, IUserRoleMappingDao userRoleMappingDao) {
    this.userDao = userDao;
    this.roleDao = roleDao;
    this.userRoleMappingDao = userRoleMappingDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.findByUsername(username);
  }

  @Transactional
  @Override
  public boolean registerUser(User user) {
    user.setPassword(user.getPassword()); //Swapping original text value with the encoded one.
    user.addAuthority(roleDao.findByRoleName(RoleEnum.ROLE_USER.name()));
    Long userId = userDao.registerUser(user);
    logger.info("Saved user id {}, now saving role mappings", userId);
    for (Role role : user.getAuthorities()) {
      Long userRoleMappingId = userRoleMappingDao.saveUserRoleMapping(userId, role.getId());
      logger.info("Saved user role mapping with id {}", userRoleMappingId);
    }
    return true;
  }
  
  @Override
  public User findByEmail(String email) {
    return userDao.findByEmail(email);
  }

  @Override
  @Transactional
  public boolean updatePassword(String username, String password) {
    User user = userDao.findByUsername(username);
    if (user != null) {
      logger.info("Encrypting password for username : {}", username);
      boolean isPwdUpdated = userDao.updatePassword(user.getId(), password);
      if (isPwdUpdated) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean isValidPassword(String rawPassword, String encodedPassword) {
    return rawPassword.equalsIgnoreCase(encodedPassword);
  }

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

}
