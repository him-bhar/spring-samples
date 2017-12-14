package com.himanshu.spring.cloud.hystrix;

import org.springframework.stereotype.Component;

public interface IFruitService {
  String fetchFruitDetails(String fruitName);
}
