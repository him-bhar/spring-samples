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

import com.himanshu.springboot2.foundation.security.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

// TODO: Auto-generated Javadoc

/**
 * The Interface IUserService.
 */
public interface IUserService extends UserDetailsService {
  
  /**
   * Register user.
   *
   * @param user the user
   * @return true, if successful
   */
  boolean registerUser(User user);

  User findByEmail(String email);

  boolean updatePassword(String username, String password);
  
  boolean isValidPassword(String rawPassword, String encodedPassword);
  
  User findByUsername(String username);
  
}
