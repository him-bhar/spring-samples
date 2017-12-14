package com.himanshu.spring.eureka.another.sample.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="a-bootiful-client")
public interface ABootifulClient {
  @RequestMapping(value = "/fruit/{fruitName}", method = RequestMethod.GET)
  String getFruitDetails(@PathVariable("fruitName") String fruit);
}
