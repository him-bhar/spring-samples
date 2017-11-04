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
package com.himanshu.projects.springsecurity.minified.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class UserRoleMappingDao.
 */
@Component
public class UserRoleMappingDao implements IUserRoleMappingDao {
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  /** The jdbc template. */
  private final JdbcTemplate jdbcTemplate;
  
  /**
   * Instantiates a new user role mapping dao.
   *
   * @param dataSource the data source
   */
  @Autowired
  public UserRoleMappingDao (@Qualifier("dataSource")DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }


  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IBaseDao#findAll()
   */
  @Override
  public List<Object> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
  
  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IUserRoleMappingDao#saveUserRoleMapping(java.lang.Long, java.lang.Long)
   */
  @Override
  public Long saveUserRoleMapping(Long userId, Long roleId) {
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_role_mapping").usingGeneratedKeyColumns("id");
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userid", userId);
    paramMap.put("roleid", roleId);
    
    KeyHolder keyHolder = insert.executeAndReturnKeyHolder(paramMap);
    return keyHolder.getKey().longValue();
  }

}
