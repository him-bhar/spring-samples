package com.himanshu.springboot2.angular;

import com.himanshu.springboot2.angular.orders.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@SpringBootApplication(scanBasePackages = {"com.himanshu.springboot2"}, exclude = DataSourceAutoConfiguration.class)
public class Main {
  private static Logger logger = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
    System.setProperty("jdbc.inmem.db", "true"); //Forcing to run in in-mem db mode.
    SpringApplication app = new SpringApplicationBuilder(Main.class).logStartupInfo(true).build();
    app.run(args);
  }

  /*@Bean
  public CommandLineRunner demo(CustomerService customerService) {
    return (args) -> {
      StreamSupport.stream(customerService.listAll().spliterator(), false)
              .map(customer -> customer.toString())
              .forEach(logger::info);

      Runnable r1 = () -> logger.info(customerService.getAndUpdateCustomerCountry(1l, "India").toString());
      Runnable r2 = () -> logger.info(customerService.getAndUpdateCustomerCountry(1l, "United States").toString());
      ExecutorService executorService = Executors.newFixedThreadPool(2);
      List<Runnable> runnables = new ArrayList<>();
      runnables.add(r1);
      runnables.add(r2);
      StreamSupport.stream(runnables.spliterator(), false).forEach(executorService::submit);
      executorService.awaitTermination(100, TimeUnit.SECONDS);
      System.exit(0);
    };
  }*/

  /*@Bean
  public CommandLineRunner demoItems(CustomerService customerService) {
    return (args) -> {
      StreamSupport.stream(customerService.getItems().spliterator(), false)
              .map(item -> item.toString())
              .forEach(logger::info);

      System.exit(0);
    };
  }*/

  @Bean
  public CommandLineRunner demoOrders(CustomerService customerService) {
    return (args) -> {
      StreamSupport.stream(customerService.getOrders().spliterator(), false)
              .map(order -> order.toString())
              .forEach(logger::info);

      System.exit(0);
    };
  }
}
