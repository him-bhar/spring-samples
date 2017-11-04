package com.himanshu.poc.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationProvider provider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .formLogin()
            .and()
              .logout().logoutUrl("/logout").permitAll().invalidateHttpSession(true).deleteCookies("springboot-sample-webapp-cookie")
            .and()
              .authorizeRequests()
              .antMatchers("/index/**").authenticated()
              .antMatchers("/swagger-ui.html", "/swagger-ui.html/**").authenticated()
              .antMatchers("/logout").permitAll()
            .and()
              .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider);
  }
}
