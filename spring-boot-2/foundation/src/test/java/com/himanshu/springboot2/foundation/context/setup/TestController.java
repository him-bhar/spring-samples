package com.himanshu.springboot2.foundation.context.setup;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Secured("ROLE_ADMIN")
  @RequestMapping(path = {"/hello"}, method = RequestMethod.GET)
  public String helloWorld() {
    return "hello world";
  }

}
