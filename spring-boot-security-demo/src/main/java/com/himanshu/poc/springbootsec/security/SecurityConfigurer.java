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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationProviderImpl authenticationProviderImpl;

  /*@Override
  protected void configure(HttpSecurity http) throws Exception {
    //http.authorizeRequests().regexMatchers("^/rest/.*").hasAnyRole("ADMIN").and().formLogin().loginPage("/login");

    //http.regexMatcher("^/secure.*").authorizeRequests().anyRequest().authenticated().and().csrf().disable();
    //http.regexMatcher("/**").csrf().disable();
  }*/

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/secure/**").authorizeRequests().anyRequest().authenticated().and().httpBasic();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProviderImpl);
  }

}
