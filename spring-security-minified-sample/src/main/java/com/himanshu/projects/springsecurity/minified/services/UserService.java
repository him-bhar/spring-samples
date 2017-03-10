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
package com.himanshu.projects.springsecurity.minified.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.himanshu.projects.springsecurity.minified.dao.IRoleDao;
import com.himanshu.projects.springsecurity.minified.dao.IUserDao;
import com.himanshu.projects.springsecurity.minified.dao.IUserRoleMappingDao;
import com.himanshu.projects.springsecurity.minified.domain.Role;
import com.himanshu.projects.springsecurity.minified.domain.RoleEnum;
import com.himanshu.projects.springsecurity.minified.domain.User;

/**
 * The Class UserService.
 */
@Component
@Qualifier("userService")
public class UserService implements IUserService {
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  /** The encoder. */
  @Autowired
  @Qualifier("passwordEncoder")
  private PasswordEncoder encoder;
  
  /** The user dao. */
  @Autowired
  private IUserDao userDao;
  
  /** The role dao. */
  @Autowired
  private IRoleDao roleDao;
  
  /** The user role mapping dao. */
  @Autowired
  private IUserRoleMappingDao userRoleMappingDao;
  
  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.findByUsername(username);
  }

  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.services.IUserService#registerUser(com.himanshu.projects.whatsmyshare.domain.User)
   */
  @Transactional
  @Override
  public boolean registerUser(User user) {
    user.setPassword(encoder.encode(user.getPassword())); //Swapping original text value with the encoded one.
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
      String encPassword = encoder.encode(password);
      boolean isPwdUpdated = userDao.updatePassword(user.getId(), encPassword);
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
    return encoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

}
