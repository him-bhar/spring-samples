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
package com.himanshu.poc.springbootsec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.poc.springbootsec.dao.PersonDao;
import com.himanshu.poc.springbootsec.service.TokenKeeperService;

@RestController
public class SampleController {
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private PersonDao personDao;
  
  @Autowired
  private TokenKeeperService tokenKeeperService;

  @RequestMapping(method = RequestMethod.GET, value = "/sample/echo/{name}")
  public String echoName(@PathVariable("name") String name) {
    logger.info("Name : " + name);
    return "Hello " + name;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/sample/echo/all")
  public String echoAllNames() {
    logger.info("personDao : " + personDao.getNames());
    return "Hello " + personDao.getNames();
  }

  @PreAuthorize(value="hasRole('ADMIN_ROLE')")
  @RequestMapping(method = RequestMethod.GET, value = "/secure/sample/test")
  public String echoSecureTest() {
    return "Hello " + personDao.getNames();
  }
  
  @PreAuthorize(value="hasRole('KKK_ROLE')") //This role does not actually exists, should always throw forbidden
  @RequestMapping(method = RequestMethod.GET, value = "/secure/sample/test/forbidden")
  public String echoForbiddenSecureTest() {
    return "Hello " + personDao.getNames();
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/secure/generate/token/{name}")
  public String getToken(@PathVariable("name") String name) {
    logger.info("Name to generate token for : " + name);
    String token = tokenKeeperService.generateNewToken(name);
    return token;
  }

}
