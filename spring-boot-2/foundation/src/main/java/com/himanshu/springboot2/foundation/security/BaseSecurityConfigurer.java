package com.himanshu.springboot2.foundation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Map;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public abstract class BaseSecurityConfigurer extends WebSecurityConfigurerAdapter {

  private final String secureUrlRegex;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  public BaseSecurityConfigurer(String secureUrlRegex) {
    this.secureUrlRegex = secureUrlRegex;
  }

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
            //.formLogin().loginPage("/login").loginProcessingUrl("/authenticate").failureUrl("/login-error").defaultSuccessUrl("/dashboard").usernameParameter("j_username").passwordParameter("j_password")
            //.and()
            //.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("true")
            //.and()
            .authorizeRequests()
              .regexMatchers(secureUrlRegex).authenticated()
              .anyRequest().authenticated()
            /*.antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .antMatchers("/api/**").hasRole("USER")
            .antMatchers("/shared/**", "/friends/**", "/events/**", "/share/**", "/profile/**", "/dashboard/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/reg/**", "/getting-started/**", "/contact-us/**", "/forgot-password/**").permitAll()*/
            .and()
              .csrf().disable()
            .exceptionHandling()
              .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
  }

  /*@Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }*/

}
