package com.himanshu.projects.springsecurity.minified.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * <p>@EnableGlobalMethodSecurity</p>
 * prePostEnabled = It is used to enable @PreAuthorize and @PostAuthrorize annotations of spring security
 * securedEnabled = It is used to enable @Secured annotation of spring security
 * <a>http://docs.spring.io/spring-security/site/docs/4.1.x-SNAPSHOT/apidocs/org/springframework/security/config/annotation/method/configuration/EnableGlobalMethodSecurity.html</a>
 * @author himanshu
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
  @Autowired
  private AuthenticationProvider authenticationProvider;
  
  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }
  
  //Caused by: java.lang.IllegalArgumentException: role should not start with 'ROLE_' since it is automatically inserted. Got 'ROLE_ADMIN'
  //Hence removing ROLE_
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //super.configure(http);
    http.addFilterAfter(new BasicAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class)
      .formLogin().loginPage("/login").loginProcessingUrl("/authenticate").failureUrl("/login-error").defaultSuccessUrl("/dashboard").usernameParameter("j_username").passwordParameter("j_password")
      .and()
      .logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("true")
      .and()
      .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/api/**").hasRole("USER")
        .antMatchers("/shared/**", "/friends/**", "/events/**", "/share/**", "/profile/**", "/dashboard/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/reg/**", "/getting-started/**", "/contact-us/**", "/forgot-password/**").permitAll()
      .and()
      .csrf().disable();
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
}
