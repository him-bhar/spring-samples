package com.himanshu.projects.springsecurity.minified.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by himanshu on 03-06-2017.
 */
public class DeferredEchoTask extends AbstractApiControllerTask<Welcome> {
  private static final Logger logger = LoggerFactory.getLogger(DeferredEchoTask.class);

  @Override
  protected void innerRun() {
    logger.info("Processing request in a different thread");
    Welcome w = new Welcome("Hello Maharaja");
    getResult().setResult(w);
  }
}
