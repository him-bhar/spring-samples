package com.himanshu.spring.cloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FruitService implements IFruitService {
  private AtomicInteger counter = new AtomicInteger(0);

  @Bean
  @LoadBalanced
  private RestTemplate createRestTemplate() {
    return new RestTemplate();
  }

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "fallbackResponse")
  @Override
  public String fetchFruitDetails(String fruitName) {
    try {
      if (counter.get() > 3 && counter.get() < 6) {
        throw new RuntimeException("Some exception!!");
      }
      System.out.println("Get fruit details for: "+fruitName);
      String response = restTemplate.exchange("http://a-bootiful-client/fruit/{fruitName}", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, fruitName).getBody();
      System.out.println("Response received is: "+response);
      return response;
    } finally {
      counter.incrementAndGet();
    }
  }

  public String fallbackResponse(String fruitName) {
    return "Responding from fallback method";
  }
}
