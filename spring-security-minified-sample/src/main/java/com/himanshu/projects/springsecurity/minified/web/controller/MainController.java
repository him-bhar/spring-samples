package com.himanshu.projects.springsecurity.minified.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @RequestMapping(method = RequestMethod.GET, value = {"/", ""})
  public String viewDashboard() {
    logger.info("Inside main controller method viewDashboard");
    return "redirect:/dashboard";
  }
  
  /** Login form. */
  @RequestMapping("/login")
  public String login(@RequestParam(value="activate_login", required=false)String activateLogin, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
      if (activateLogin != null && "TRUE".equalsIgnoreCase(activateLogin)) {
        model.addAttribute("actionSuccessful", true);
        model.addAttribute("actionMsg", "Please check your inbox for activation mail.");
      }
      return "login";
    } else {
      return "redirect:/";
    }
  }
  
  /** Login form with error. */
  @RequestMapping("/login-error")
  public String loginError(HttpServletRequest request, Model model) {
    model.addAttribute("loginError", true);
    Throwable securityThrowable = (Throwable) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    if (securityThrowable instanceof BadCredentialsException) {
      model.addAttribute("loginErrorMsg", "Username or password not valid.");
    } else if (securityThrowable instanceof AuthenticationServiceException) {
      model.addAttribute("loginErrorMsg", "Internal server error. Please contact admin.");
    } else if (securityThrowable instanceof DisabledException) {
      model.addAttribute("loginErrorMsg", "User is not yet active.");
    } else if (securityThrowable instanceof LockedException) {
      model.addAttribute("loginErrorMsg", "User is locked.");
    } else {
      model.addAttribute("loginErrorMsg", "Username or password not valid.");
    }
    logger.error("Error in logs during login ", securityThrowable);
    return "login";
  }

  /** Simulation of an exception. */
  @RequestMapping("/simulateError.html")
  public void simulateError() {
    throw new RuntimeException("This is a simulated error message");
  }
  
  @RequestMapping(value={"/getting-started","/getting-started/"})
  public String gettingStartedPage() {
    return "getting-started/intro";
  }

  /** Error page. */
  @RequestMapping("/error")
  public String error(HttpServletRequest request, Model model) {
    model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
    logger.error("Error in logs ", throwable);
    StringBuilder errorMessage = new StringBuilder();
    errorMessage.append("<ul>");
    while (throwable != null) {
      errorMessage.append("<li>").append(escapeTags(throwable.getMessage())).append("</li>");
      throwable = throwable.getCause();
    }
    errorMessage.append("</ul>");
    model.addAttribute("errorMessage", errorMessage.toString());
    return "error";
  }
  
  /** Substitute 'less than' and 'greater than' symbols by its HTML entities. */
  private String escapeTags(String text) {
    if (text == null) {
      return null;
    }
    return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
}
