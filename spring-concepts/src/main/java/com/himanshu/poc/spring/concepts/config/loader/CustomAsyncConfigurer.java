package com.himanshu.poc.spring.concepts.config.loader;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * This class implements {@link AsyncConfigurer}, it gives us the benefit of customizing the executor we are using 
 * and how to handle excpetion during async calls.
 *  
 * @author himanshu
 *
 */
public class CustomAsyncConfigurer implements AsyncConfigurer {
  @Override
  public Executor getAsyncExecutor() {
    AtomicInteger threadCounter = new AtomicInteger(1);
    return new ThreadPoolExecutor(2, 3, 100l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (r) -> {return new Thread(r, "MyCustomExecutor-"+threadCounter.getAndIncrement());});
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    Logger logger = LoggerFactory.getLogger(CustomAsyncConfigurer.class.getName().concat("ExceptionHandler"));
    AsyncUncaughtExceptionHandler asyncExceptionHandler = (Throwable paramThrowable, Method paramMethod, Object[] paramArrayOfObject) -> {
      logger.error("Caught Exception: "+paramThrowable);
      //throw new RuntimeException(paramThrowable);
    };
    return asyncExceptionHandler;
  }
}
