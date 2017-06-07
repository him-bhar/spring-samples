package com.himanshu.projects.springsecurity.minified.web.controller;

/**
 * Created by himanshu on 03-06-2017.
 */
public class DeferredEchoTask extends AbstractApiControllerTask<Welcome> {
  @Override
  protected void innerRun() {
    Welcome w = new Welcome("Hello Maharaja");
    getResult().setResult(w);
  }
}
