package com.himanshu.poc.spring.samples.springbootwebreactive.controller.v2;

import com.himanshu.poc.spring.samples.springbootwebreactive.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

public class RouterConfig {
  @Bean
  RouterFunction<?> routes(UserHandler userHandler) {
    return RouterFunctions.route(RequestPredicates.GET("/user/v2/"), userHandler::all)
            .andRoute(RequestPredicates.GET("/user/v2/{id}"), userHandler::byId)
            .andRoute(RequestPredicates.GET("/user/v2/event/stream"), userHandler::steamEvents);
  }
}
