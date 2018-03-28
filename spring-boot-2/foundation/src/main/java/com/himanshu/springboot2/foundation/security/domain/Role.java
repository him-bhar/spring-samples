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
package com.himanshu.springboot2.foundation.security.domain;

import org.springframework.security.core.GrantedAuthority;

// TODO: Auto-generated Javadoc

/**
 * The Class Role.
 */
public class Role implements GrantedAuthority {
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Role [id=" + id + ", roleName=" + roleName + "]";
  }

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  /** The id. */
  private Long id;
  
  /** The role name. */
  private String roleName;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the role name.
   *
   * @return the role name
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * Sets the role name.
   *
   * @param roleName the new role name
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.GrantedAuthority#getAuthority()
   */
  @Override
  public String getAuthority() {
    return roleName;
  }

}
