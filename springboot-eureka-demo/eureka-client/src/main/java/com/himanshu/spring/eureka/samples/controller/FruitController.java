package com.himanshu.spring.eureka.samples.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FruitController {
  private Map<String, Fruit> fruitMap = Maps.newHashMap();
  {
    fruitMap.put("apple", new Fruit("apple", "red", true));
    fruitMap.put("orange", new Fruit("orange", "orange", true));
    fruitMap.put("grapes", new Fruit("grapes", "green", false));
  }

  @RequestMapping(value = "/fruit/{name}", method = RequestMethod.GET)
  public Fruit getFruitDetail(@PathVariable("name") String nameOfFruit) {
    System.out.println("Fetching fruit details from map: "+nameOfFruit);
    return fruitMap.get(nameOfFruit);
  }
}
