package com.himanshu.spring.cloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyController {
  @Autowired
  private IFruitService fruitService;

  @Autowired
  private ABootifulClient bootifulClient;

  @RequestMapping(value = "/fruit/{fruitName}", method = RequestMethod.GET)
  private String getFruitDetails(@PathVariable("fruitName") String fruit) {
    return fruitService.fetchFruitDetails(fruit);
  }

  @RequestMapping(value = "/feign/fruit/{fruitName}", method = RequestMethod.GET)
  private String getFruitDetailsFromFeign(@PathVariable("fruitName") String fruit) {
    return bootifulClient.getFruitDetails(fruit);
  }


}
