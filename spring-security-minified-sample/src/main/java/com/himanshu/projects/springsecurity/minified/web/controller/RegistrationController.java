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
package com.himanshu.projects.springsecurity.minified.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.himanshu.projects.springsecurity.minified.domain.User;
import com.himanshu.projects.springsecurity.minified.services.IUserService;
import com.himanshu.projects.springsecurity.minified.utils.UserNameValidator;

/**
 * The Class ProfileController.
 */
@Controller
@RequestMapping("reg")
public class RegistrationController {
  
  /** The user service. */
  @Autowired
  private IUserService userService;
  
  @Autowired
  private AuthenticationProvider authProvider;
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  /**
   * Open register.
   *
   * @param model the model
   * @return the string
   */
  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String openRegister(Model model) {
    model.addAttribute("regBean", new RegistrationBean());
    return "profile/register";
  }
  
  /**
   * Complete register.
   *
   * @param model the model
   * @param request the request
   * @param regBean the reg bean
   * @param bindingResult the binding result
   * @return the string
 * @throws IOException 
 * @throws ServletException 
   */
  @RequestMapping(value = "/completereg", method = RequestMethod.POST)
  public String completeRegister(Model model, HttpServletRequest request, @Valid @ModelAttribute("regBean") RegistrationBean regBean, BindingResult bindingResult, HttpServletResponse response) throws ServletException, IOException {
    logger.info("Binding result details: {} ", bindingResult.getAllErrors());
    if (! bindingResult.hasErrors()) {
      boolean isUsernameValid = UserNameValidator.validateUserName(regBean.getUsername());
      if (!isUsernameValid) {
        logger.info("Username not valid as per regex : {}", regBean.getUsername());
        bindingResult.addError(new FieldError("regBean", "username", "Username not valid. Should be 3-15 in length and contain only, [a-z], [0-9], '-' and '_'"));
      }
      logger.info("Since form validation was successful, going for password matching.");
      if (! regBean.getPassword().equals(regBean.getRePassword())) {
        logger.info("Password mismatch");
        bindingResult.addError(new FieldError("regBean", "rePassword", "Password Matching failed."));
      }
    }
    if (bindingResult.hasErrors()) {
      model.addAttribute("regBean", regBean);
      return "profile/register";
    } else {
      logger.info("All validations successful, now registering the user");
      User user = new User();
      user.setEmail(regBean.getEmail());
      user.setPassword(regBean.getPassword());
      user.setUsername(regBean.getUsername());
      userService.registerUser(user);
      doAutoLogin(request, regBean.getUsername(), regBean.getPassword());
      return "redirect:/login?activate_login=true";
    }
  }

  private void doAutoLogin(HttpServletRequest request, String username, String password) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    // generate session if one doesn't exist
    request.getSession();
    token.setDetails(new WebAuthenticationDetails(request));
    Authentication authenticatedUser = authProvider.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
  }
  
}
