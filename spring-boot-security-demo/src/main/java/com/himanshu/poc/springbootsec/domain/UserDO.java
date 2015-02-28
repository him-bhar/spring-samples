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
package com.himanshu.poc.springbootsec.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserDO implements Authentication {
  
  private String name;
  private String credentials;
  private Set<Role> roles;
  private boolean authenticated = false;

  @Override
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }
  
  public void setCredentials(String credentials) {
    this.credentials = credentials;
  }

  @Override
  public Object getDetails() {
    return name;
  }

  @Override
  public Object getPrincipal() {
    return this.name;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }
  
  public void addRole(Role r) {
    if (this.roles == null) {
      roles = new HashSet<Role>();
    }
    roles.add(r);
  }

  @Override
  public String toString() {
    return "UserDO [name=" + name + ", roles=" + roles + ", authenticated=" + authenticated + "]";
  }

}
