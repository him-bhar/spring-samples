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
package com.himanshu.poc.springbootsec.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.himanshu.poc.springbootsec.domain.Role;
import com.himanshu.poc.springbootsec.domain.UserDO;

@Repository
public class UsersDao {

  private static String allUsersSql = "select a.username, a.password, r.role_name from users a " 
                               + " LEFT JOIN user_role_mapping rum ON (a.username=rum.username) "
                               + " LEFT JOIN roles r ON (rum.role_name=r.role_name) ";
  
  private static String specificUserSql = allUsersSql.concat(" where a.username=:username ");

  private Logger logger = LoggerFactory.getLogger(PersonDao.class);

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  
  @PostConstruct
  public void init() {
    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
  }
 
  public Collection<UserDO> getAll() {
    Map<String, UserDO> usersMap = new HashMap<String, UserDO>();
    jdbcTemplate.query(allUsersSql, new UserRowMapper(usersMap));
    logger.info("Total records found : " + usersMap.size());
    return usersMap.values();
  }
  
  public UserDO getUserByUserName(String username) {
    Map<String, UserDO> usersMap = new HashMap<String, UserDO>();
    MapSqlParameterSource sqlParams = new MapSqlParameterSource();
    sqlParams.addValue("username", username);
    namedParameterJdbcTemplate.query(specificUserSql, sqlParams, new UserRowMapper(usersMap));
    logger.info("Total records found : " + usersMap.size());
    return usersMap.get(username);
  }

  private class UserRowMapper implements RowMapper<UserDO> {
    
    private final Map<String, UserDO> userMap;
    
    public UserRowMapper(Map<String, UserDO> userMap) {
      this.userMap =  userMap;
    }
    
    @Override
    public UserDO mapRow(ResultSet arg0, int arg1) throws SQLException {
      String username = arg0.getString("username");
      if (!userMap.containsKey(username) || userMap.get(username) == null) {
        String password = arg0.getString("password");
        UserDO user = new UserDO();
        user.setName(username);
        user.setCredentials(password);
        userMap.put(username, user);
      }
      String role = arg0.getString("role_name");
      Role r = new Role(role);
      userMap.get(username).addRole(r);
      return userMap.get(username);
    }
  }

}
