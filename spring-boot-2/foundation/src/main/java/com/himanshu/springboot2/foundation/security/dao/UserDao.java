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
package com.himanshu.springboot2.foundation.security.dao;

import com.himanshu.springboot2.foundation.security.domain.Role;
import com.himanshu.springboot2.foundation.security.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class UserDao.
 */
public class UserDao implements IUserDao {
  
  private String keyTypeUsername = "USERNAME";
  
  private String keyTypeEmail = "EMAIL";
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  /** The jdbc template. */
  private final JdbcTemplate jdbcTemplate;
  
  /**
   * Instantiates a new user dao.
   *
   * @param dataSource the data source
   */
  public UserDao (DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IBaseDao#findAll()
   */
  @Override
  public List<User> findAll() {
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IUserDao#findByUsername(java.lang.String)
   */
  @Override
  public User findByUsername(final String username) {
    Map<String, User> userMap = new HashMap<>();
    String sql = "select u.*, r.id as roleid, r.name as rolename from user u, role r, user_role_mapping urm where u.id=urm.userid and r.id=urm.roleid and username=?";
    jdbcTemplate.query(sql, new PreparedStatementSetter() {
      
      @Override
      public void setValues(PreparedStatement arg0) throws SQLException {
        arg0.setString(1, username);
        
      }
    }, new UserRowMapper(userMap, keyTypeUsername));
    
    logger.info("Looking for username : {} found {} matches.", username, userMap.size());
    Assert.isTrue(userMap.size() <= 1, String.format("Queried result cannot be greater than 1, verify for username %s", username));
    
    return userMap.get(username);
  }
  
  /**
   * The Class UserRowMapper.
   */
  private class UserRowMapper implements RowMapper<User> {
    
    /** The user map. */
    private Map<String, User> userMap;
    
    private String keyType;
    
    /**
     * Instantiates a new user row mapper.
     *
     * @param userMap the user map
     */
    public UserRowMapper(Map<String, User> userMap, String keyType) {
      this.userMap = userMap;
      this.keyType = keyType;
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      
      String username = rs.getString("username").intern();
      String email = rs.getString("email");
      String key = null;
      if (keyType.equalsIgnoreCase(keyTypeEmail)) {
        key = email;
      } else if (keyType.equalsIgnoreCase(keyTypeUsername)) {
        key = username;
      } else {
        throw new IllegalArgumentException("Key type cannot be null");
      }
      
      String rolename = rs.getString("rolename");
      Long roleId = rs.getLong("roleid");
      Role r = new Role();
      r.setId(roleId);
      r.setRoleName(rolename);
      
      if (userMap.containsKey(key)) {
        userMap.get(key).addAuthority(r);
      } else {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username").intern());
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAccountNonExpired(rs.getBoolean("nonexpired"));
        user.setAccountNonLocked(rs.getBoolean("nonlocked"));
        user.setCredentialsNonExpired(rs.getBoolean("credentialsnonexpired"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.addAuthority(r);
        
        userMap.put(key, user);
      }
      return userMap.get(key);
    }
    
  }

  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IUserDao#registerUser(com.himanshu.projects.whatsmyshare.domain.User)
   */
  @Override
  public Long registerUser(User user) {
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user").usingGeneratedKeyColumns("id");
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("username", user.getUsername());
    paramMap.put("password", user.getPassword());
    paramMap.put("email", user.getEmail());
    paramMap.put("nonlocked", 1);
    paramMap.put("nonexpired", 1);
    paramMap.put("credentialsnonexpired", 1);
    paramMap.put("enabled", 0);
    
    KeyHolder keyHolder = insert.executeAndReturnKeyHolder(paramMap);
    return keyHolder.getKey().longValue();
  }
  
  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IUserDao#enableUser(java.lang.Long)
   */
  @Override
  public boolean enableUser(final Long userId) {
    String sql = "update user set enabled=1 where id=?";
    int numOfRowsUpdated = jdbcTemplate.update(sql, new PreparedStatementSetter() {
      
      @Override
      public void setValues(PreparedStatement arg0) throws SQLException {
        arg0.setLong(1, userId);
      }
    });
    if (numOfRowsUpdated > 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public User findByEmail(final String email) {
    Map<String, User> userMap = new HashMap<>();
    String sql = "select u.*, r.id as roleid, r.name as rolename from user u, role r, user_role_mapping urm where u.id=urm.userid and r.id=urm.roleid and email=?";
    jdbcTemplate.query(sql, new PreparedStatementSetter() {
      
      @Override
      public void setValues(PreparedStatement arg0) throws SQLException {
        arg0.setString(1, email);
        
      }
    }, new UserRowMapper(userMap, keyTypeEmail));
    
    logger.info("Looking for email : {} found {} matches.", email, userMap.size());
    Assert.isTrue(userMap.size() <= 1, String.format("Queried result cannot be greater than 1, verify for email %s", email));
    
    return userMap.get(email);
  }

  @Override
  public boolean updatePassword(final Long userId, final String encPassword) {
    String sql = "update user set password=? where id=?";
    int numOfRowsUpdated = jdbcTemplate.update(sql, new PreparedStatementSetter() {
      
      @Override
      public void setValues(PreparedStatement arg0) throws SQLException {
        arg0.setString(1, encPassword);
        arg0.setLong(2, userId);
      }
    });
    if (numOfRowsUpdated > 0) {
      return true;
    } else {
      return false;
    }
  }

}