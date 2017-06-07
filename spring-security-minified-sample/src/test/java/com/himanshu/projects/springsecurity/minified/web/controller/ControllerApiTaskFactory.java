package com.himanshu.projects.springsecurity.minified.web.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by himanshu on 03-06-2017.
 */
public class ControllerApiTaskFactory {
  private final ExecutorService executor;

  public ControllerApiTaskFactory() {
    this.executor = Executors.newFixedThreadPool(3, (Runnable r) -> {return new Thread(r, "API-SERVICE-THREAD");});
  }

  public ApiControllerTask<Welcome> createEchoTask() {
    return new DeferredEchoTask();
  }

  public void submit(ApiControllerTask task) {
    executor.submit(task);
  }
}
