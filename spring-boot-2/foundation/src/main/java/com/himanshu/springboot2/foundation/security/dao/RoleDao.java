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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class RoleDao.
 */
public class RoleDao implements IRoleDao {
  
/** The logger. */
private Logger logger = LoggerFactory.getLogger(getClass());
  
  /** The jdbc template. */
  private final JdbcTemplate jdbcTemplate;
  
  /**
   * Instantiates a new role dao.
   *
   * @param dataSource the data source
   */
  public RoleDao (DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IBaseDao#findAll()
   */
  @Override
  public List<Role> findAll() {
    String sql = "select id, name from role";
    List<Role> roles = jdbcTemplate.query(sql, new RoleRowMapper());
    return roles;
  }
  
  /* (non-Javadoc)
   * @see com.himanshu.projects.whatsmyshare.dao.IRoleDao#findByRoleName(java.lang.String)
   */
  @Override
  public Role findByRoleName(final String rolename) {
    String sql = "select id, name from role where name=?";
    List<Role> roles = jdbcTemplate.query(sql, new PreparedStatementSetter() {
      
      @Override
      public void setValues(PreparedStatement arg0) throws SQLException {
        arg0.setString(1, rolename);
      }
    }, new RoleRowMapper());
    
    if (roles.isEmpty()) {
      logger.error("No matching role found in db {}", rolename);
      throw new IllegalArgumentException(String.format("Cannot register user as role %s is not available", rolename));
    }
    Assert.isTrue(roles.size() <= 1, String.format("Duplicate role found %s", rolename));
    return roles.get(0);
  }
  
  /**
   * The Class RoleRowMapper.
   */
  private class RoleRowMapper implements RowMapper<Role> {

    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
      Role r = new Role();
      r.setId(rs.getLong("id"));
      r.setRoleName(rs.getString("name"));
      return r;
    }
    
  }

}
