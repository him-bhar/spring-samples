package com.himanshu.poc.calculator;

public interface ICalculator<T> {
  java.util.concurrent.Future<T> calculate (T a, T b);
}
