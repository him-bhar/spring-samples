package com.himanshu.poc.spring.async;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.himanshu.poc.calculator.ICalculator;
import com.himanshu.poc.spring.concepts.config.loader.CalculatorConfigurer;
import com.himanshu.poc.spring.concepts.config.loader.CustomAsyncConfigurer;
import com.himanshu.poc.spring.concepts.config.loader.EnableAsyncSpringConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={EnableAsyncSpringConfigurer.class, CalculatorConfigurer.class, CustomAsyncConfigurer.class})
public class TestAsyncCalculatorSpringCalls {
  
  private static Logger logger = LoggerFactory.getLogger(TestAsyncCalculatorSpringCalls.class);
  
  @Autowired
  private ICalculator add;
  
  @Autowired
  private ICalculator subtract;
  
  @Autowired
  private ICalculator multiply;
  
  @Autowired
  private ICalculator divide;
  
  @Test
  public void testAdd() throws InterruptedException, ExecutionException {
    logger.info(String.valueOf(add.calculate(10, 20).get()));
  }
  
  @Test
  public void testSubtract() throws InterruptedException, ExecutionException {
    logger.info(String.valueOf(subtract.calculate(10, 20).get()));
  }
  
  @Test
  public void testMultiply() throws InterruptedException, ExecutionException {
    logger.info(String.valueOf(multiply.calculate(10, 20).get()));
  }
  
  @Test
  public void testDivide() throws InterruptedException, ExecutionException {
    logger.info(String.valueOf(divide.calculate(10, 20).get()));
  }
}
