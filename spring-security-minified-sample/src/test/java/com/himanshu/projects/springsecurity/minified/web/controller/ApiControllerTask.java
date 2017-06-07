package com.himanshu.projects.springsecurity.minified.web.controller;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by himanshu on 03-06-2017.
 */
public interface ApiControllerTask<T> extends Runnable {
  String getId();
  DeferredResult<T> getResult();
}
