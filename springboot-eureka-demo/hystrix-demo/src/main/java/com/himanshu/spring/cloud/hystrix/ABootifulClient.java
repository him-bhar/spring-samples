package com.himanshu.spring.cloud.hystrix;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="a-bootiful-client", fallback = ABootifulClient.ABootifulClientFallback.class)
public interface ABootifulClient {
  @RequestMapping(value = "/fruit/{fruitName}", method = RequestMethod.GET)
  String getFruitDetails(@PathVariable("fruitName") String fruit);

  @Component
  public static class ABootifulClientFallback implements ABootifulClient {
    @Override
    public String getFruitDetails(String fruit) {
      return "Returning from feign hystrix fallback";
    }
  }
}
