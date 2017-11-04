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
package com.himanshu.poc.springbootsec;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.himanshu.poc.springbootsec.dao.UsersDao;
import com.himanshu.poc.springbootsec.domain.UserDO;
import com.himanshu.poc.springbootsec.starter.SampleWebStarter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebStarter.class)
public class UserDaoTest {
  private Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

  @Autowired
  private UsersDao userDao;


  @Test
  public void testGetAll() {
    Assert.assertNotNull(userDao);
    Collection<UserDO> users = userDao.getAll();
    logger.info(users.toString());
    Assert.assertNotNull(users);
    Assert.assertTrue(users.size() == 3);
  }
  
  @Test
  public void testGetByUserName() {
    Assert.assertNotNull(userDao);
    UserDO user = userDao.getUserByUserName("Himanshu");
    logger.info(user.toString());
    Assert.assertNotNull(user);
  }

}
