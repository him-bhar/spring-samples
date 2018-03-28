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

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class User.
 */
public class User implements UserDetails {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  /** The id. */
  private Long id;
  
  /** The username. */
  private String username;
  
  /** The password. */
  private String password;
  
  /** The email. */
  private String email;
  
  /** The account non expired. */
  private boolean accountNonExpired;
  
  /** The account non locked. */
  private boolean accountNonLocked;
  
  /** The credentials non expired. */
  private boolean credentialsNonExpired;
  
  /** The enabled. */
  private boolean enabled;
  
  /** The authorities. */
  private List<Role> authorities;
  
  /**
   * Instantiates a new user.
   */
  public User() {
    authorities = new ArrayList<>();
  }
  
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
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
   */
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  /**
   * Sets the account non expired.
   *
   * @param accountNonExpired the new account non expired
   */
  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
   */
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  /**
   * Sets the account non locked.
   *
   * @param accountNonLocked the new account non locked
   */
  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
   */
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  /**
   * Sets the credentials non expired.
   *
   * @param credentialsNonExpired the new credentials non expired
   */
  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets the enabled.
   *
   * @param enabled the new enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
   */
  public Collection<Role> getAuthorities() {
    return authorities;
  }

  /**
   * Sets the authorities.
   *
   * @param authorities the new authorities
   */
  public void setAuthorities(List<Role> authorities) {
    this.authorities = authorities;
  }
  
  /**
   * Adds the authority.
   *
   * @param authority the authority
   */
  public void addAuthority(Role authority) {
    this.authorities.add(authority);
  }
  
  /**
   * Adds the authorities.
   *
   * @param authorities the authorities
   */
  public void addAuthorities(List<Role> authorities) {
    this.authorities.addAll(authorities);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
        + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", authorities=" + authorities + "]";
  }
  
}
