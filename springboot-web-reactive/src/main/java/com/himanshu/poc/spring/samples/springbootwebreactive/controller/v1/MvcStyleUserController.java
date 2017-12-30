package com.himanshu.poc.spring.samples.springbootwebreactive.controller.v1;

import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import com.himanshu.poc.spring.samples.springbootwebreactive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
@RequestMapping(path = "/user/v1")
public class MvcStyleUserController {
  @Autowired
  private UserRepository userRepository;
  @GetMapping(path = "/{id}")
  public Mono<User> byId(@PathVariable(value = "id") long id) {
    return Mono.justOrEmpty(userRepository.findById(id));
  }

  @GetMapping(path = "/")
  public Flux<User> getAll() {
    return Flux.fromIterable(userRepository.findAll());
  }

  @GetMapping(path= "/event/stream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
  public Flux<User> streamUsers() {
    Flux<User> users = Flux.fromIterable(userRepository.findAll());
    Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(5l));
    return Flux.zip(users, intervalFlux).map(Tuple2::getT1);
  }

}
