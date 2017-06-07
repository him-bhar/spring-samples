package com.himanshu.projects.springsecurity.minified.web.controller;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Welcome welcome = (Welcome) o;
    return Objects.equals(msg, welcome.msg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msg);
  }
}