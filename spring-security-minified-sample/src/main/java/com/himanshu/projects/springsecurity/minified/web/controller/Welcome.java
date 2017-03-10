package com.himanshu.projects.springsecurity.minified.web.controller;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Welcome {
  public Welcome() {
    // TODO Auto-generated constructor stub
  }
  private String msg;

  public Welcome(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}