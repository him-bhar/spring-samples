/*
 * Copyright 2013 Himanshu Bhardwaj
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
package com.himanshu.projects.springsecurity.minified.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.himanshu.projects.springsecurity.minified.services.IUserService;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
  
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private IUserService userService;
  
  @Override
  public Authentication authenticate(Authentication arg0) throws AuthenticationException {
    logger.info(" User name is : " + arg0.getPrincipal());
    /*if (arg0.getName() == null || arg0.getName().isEmpty()) {
      //Token Based Authentication required
      logger.info("Since username is null or empty, hence token based authentication will be required");
      String tokenStr = (String)arg0.getCredentials();
      String userName = tokenKeeperService.queryUserByToken(tokenStr);
      
      UserDO user = userDao.getUserByUserName(userName);
      logger.info("Auth success");
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getPrincipal(), user.getCredentials(), user.getAuthorities());
      return token;
      
    } else {
      //Normal Authentication
      logger.info("Since username is NOT null, hence username/password based authentication will be required");
      UserDO user = userDao.getUserByUserName(arg0.getName());
      
      if (user != null && user.getCredentials().equals(arg0.getCredentials())) {
        logger.info("Auth success");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getPrincipal(), user.getCredentials(), user.getAuthorities());
        return token;
      }
    }*/
    UserDetails user = userService.loadUserByUsername((String)arg0.getPrincipal());
    if (user != null) {
      logger.info("Auth success");
      boolean isPasswordValid = userService.isValidPassword((String)arg0.getCredentials(), user.getPassword());
      if (!isPasswordValid) {
        throw new BadCredentialsException("Login failed. Bad password.");
      }
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, (String)arg0.getCredentials(), user.getAuthorities());
      return token;
    }
    logger.error("Auth failed");
    throw new AuthenticationCredentialsNotFoundException(String.format("Login failed. User %1$s not found.", (String)arg0.getPrincipal()));
  }

  @Override
  public boolean supports(Class<?> arg0) {
    return true;
  }

}
