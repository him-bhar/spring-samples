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

import java.util.stream.StreamSupport;

@SpringBootApplication(scanBasePackages = {"com.himanshu.springboot2"}, exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
public class Main {
  private static Logger logger = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
    System.setProperty("jdbc.inmem.db", "true"); //Forcing to run in in-mem db mode.
    SpringApplication app = new SpringApplicationBuilder(Main.class).logStartupInfo(true).build();
    app.run(args);
  }

  @Bean
  public CommandLineRunner demo(CustomerService customerService) {
    return (args) -> {
      StreamSupport.stream(customerService.listAll().spliterator(), false)
              .map(customer -> customer.toString())
              .forEach(logger::info);

      logger.info(customerService.getAndUpdateCustomer().toString());
    };
  }
}
