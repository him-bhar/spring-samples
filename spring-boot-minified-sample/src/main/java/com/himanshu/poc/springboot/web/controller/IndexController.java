package com.himanshu.poc.springboot.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.annotation.XmlRootElement;

@Controller
public class IndexController {
  @RequestMapping("/index")
  public String index() {
    System.out.println("IndexController3");
    return "hello";
  }

  @RequestMapping(value = "/index/api", method = RequestMethod.GET, produces = "application/xml")
  public ResponseEntity<Person> api() {
    Person p = new Person();
    p.setFirstName("Himanshu");
    p.setLastName("Tiger");
    return new ResponseEntity<Person>(p, HttpStatus.OK);
  }

  @XmlRootElement
  private static class Person {
    private String firstName;
    private String lastName;

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
  }
}
