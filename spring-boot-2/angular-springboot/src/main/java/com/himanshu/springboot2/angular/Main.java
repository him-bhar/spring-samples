package com.himanshu.springboot2.angular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"com.himanshu.springboot2"})
public class Main {
  public static void main(String[] args) {
    System.setProperty("jdbc.inmem.db", "true"); //Forcing to run in in-mem db mode.
    SpringApplication app = new SpringApplicationBuilder(Main.class).logStartupInfo(true).build();
    app.run(args);
  }
}
