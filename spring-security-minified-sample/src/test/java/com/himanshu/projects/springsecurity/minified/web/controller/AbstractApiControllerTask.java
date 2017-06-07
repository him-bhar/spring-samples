package com.himanshu.projects.springsecurity.minified.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

/**
 * Created by himanshu on 03-06-2017.
 */
public abstract class AbstractApiControllerTask<T> implements ApiControllerTask {
  private static final Logger logger = LoggerFactory.getLogger(AbstractApiControllerTask.class);
  private final String id;
  private DeferredResult<Welcome> deferredResult;
  public AbstractApiControllerTask() {
    id = UUID.randomUUID().toString();
    deferredResult = new DeferredResult<>();
  }

  @Override
  public void run() {
    logger.info("Generating response for task Id: {}", getId());
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public DeferredResult getResult() {
    return deferredResult;
  }

  protected abstract void innerRun();
}
