package com.himanshu.poc.spring.samples.springbootwebreactive.controller.v2;

import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import com.himanshu.poc.spring.samples.springbootwebreactive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@Component
public class UserHandler {
  @Autowired
  private UserRepository userRepository;

  public Mono<ServerResponse> byId(ServerRequest request) {
    return ServerResponse.ok().body(Mono.justOrEmpty(userRepository.findById(Long.parseLong(request.pathVariable("id")))), User.class);
  }

  public Mono<ServerResponse> all(ServerRequest request) {
    return ServerResponse.ok().body(Flux.fromIterable(userRepository.findAll()), User.class);
  }

  public Mono<ServerResponse> steamEvents(ServerRequest request) {
    return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(
              Flux.fromIterable(userRepository.findAll()).zipWith(Flux.interval(Duration.ofSeconds(5l))).map(Tuple2::getT1), User.class
            );
  }
}
