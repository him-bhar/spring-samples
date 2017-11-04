package com.himanshu.poc.spring.concepts.config.loader;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncResult;

import com.himanshu.poc.calculator.AddCalculator;
import com.himanshu.poc.calculator.DivideCalculator;
import com.himanshu.poc.calculator.ICalculator;
import com.himanshu.poc.calculator.MultiplyCalculator;
import com.himanshu.poc.calculator.SubtractCalculator;

public class CalculatorConfigurer {
  
  @Bean
  public ICalculator add() {
    //return new AddCalculator();
    ICalculator<Integer> add = (Integer a, Integer b) -> {return new AsyncResult<Integer>(a+b);};
    return add;
  }
  
  @Bean
  public ICalculator subtract() {
    return new SubtractCalculator();
  }
  
  @Bean
  public ICalculator multiply() {
    return new MultiplyCalculator();
  }
  
  @Bean
  public ICalculator divide() {
    return new DivideCalculator();
  }

}
