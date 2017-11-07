package com.himanshu.spring.eureka.another.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AnotherFruitController {
  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping(value = "/fetch/{fruitName}", method = RequestMethod.GET)
  public String getFruitDetails(@PathVariable("fruitName") String fruit) {
    System.out.println("Get fruit details for: "+fruit);
    String response = restTemplate.exchange("http://a-bootiful-client/fruit/{fruitName}", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, fruit).getBody();
    System.out.println("Response received is: "+response);
    return response;
  }

  @Bean
  @LoadBalanced
  private RestTemplate createRestTemplate() {
    return new RestTemplate();
  }
}
