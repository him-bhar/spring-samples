package com.himanshu.poc.spring.samples.springbootwebreactive.controller.v1;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.InMemDBConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.controller.v2.RouterConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigLoader.class, InMemDBConfig.class, RouterConfig.class})
@ActiveProfiles("test")
public class MvcStyleUserControllerTest {
  @Autowired
  ApplicationContext context;

  WebTestClient rest;

  @Before
  public void setup() {
    this.rest = WebTestClient
            .bindToApplicationContext(this.context)
            .configureClient()
            .build();
  }

  @Test
  public void getAllUsersShouldBeOk() throws Exception {
    this.rest
            .get()
            .uri("/user/v1/")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(User.class);
  }

  @Test
  public void getUserByIdShouldBeOk() throws Exception {
    this.rest
            .get()
            .uri("/user/v1/{id}", 2)
            .exchange()
            .expectStatus().isOk()
            .expectBody(User.class);
  }

}
