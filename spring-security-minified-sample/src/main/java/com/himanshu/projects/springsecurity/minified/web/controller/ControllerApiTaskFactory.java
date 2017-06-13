package com.himanshu.projects.springsecurity.minified.web.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by himanshu on 03-06-2017.
 */
public class ControllerApiTaskFactory {
  private final ExecutorService executor;
  private AtomicInteger threadCounter;

  public ControllerApiTaskFactory() {
    threadCounter = new AtomicInteger(0);
    this.executor = Executors.newFixedThreadPool(3, (Runnable r) -> {return new Thread(r, "API-SERVICE-THREAD-"+threadCounter.incrementAndGet());});
  }

  public ApiControllerTask<Welcome> createEchoTask() {
    return new DeferredEchoTask();
  }

  public void submit(ApiControllerTask task) {
    executor.submit(task);
  }
}
