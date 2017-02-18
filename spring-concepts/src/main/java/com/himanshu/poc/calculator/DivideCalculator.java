package com.himanshu.poc.calculator;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

public class DivideCalculator implements ICalculator<Integer> {
  
  private static Logger logger = LoggerFactory.getLogger(DivideCalculator.class);

  @Async
  @Override
  public Future<Integer> calculate(Integer a, Integer b) {
    logger.info("Dividing numbers: {}, {}", a, b);
    return new AsyncResult<Integer>(a/b);
  }

}
