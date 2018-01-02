package com.himanshu.poc.spring.samples.springbootwebreactive.controller.v1;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.InMemDBConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.controller.v2.RouterConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigLoader.class, InMemDBConfig.class, RouterConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MvcStyleUserControllerIntegrationTest {

  @LocalServerPort
  private int port;

  private WebTestClient rest;

  @Before
  public void setup() {
    this.rest = WebTestClient
            .bindToServer()
            .responseTimeout(Duration.ofDays(1))
            .baseUrl("http://localhost:"+port)
            //.filter(ExchangeFilterFunctions.basicAuthentication())
            .build();
  }

  @Test
  public void getAllShouldBeOk() throws Exception {
    this.rest
            .get()
            .uri("/user/v1/")
            .exchange()
            .expectStatus().isOk();
  }
}
