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
package com.himanshu.poc.springbootsec.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.himanshu.poc.springbootsec.dao.UsersDao;
import com.himanshu.poc.springbootsec.domain.UserDO;
import com.himanshu.poc.springbootsec.service.TokenKeeperService;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
  
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private UsersDao userDao;
  
  @Autowired
  private TokenKeeperService tokenKeeperService;

  @Override
  public Authentication authenticate(Authentication arg0) throws AuthenticationException {
    logger.info(" User name is : " + arg0.getName());
    if (arg0.getName() == null || arg0.getName().isEmpty()) {
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
    }
    
    logger.error("Auth failed");
    return null;
  }

  @Override
  public boolean supports(Class<?> arg0) {
    // TODO Auto-generated method stub
    return true;
  }

}
