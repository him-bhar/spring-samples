package com.himanshu.poc.springboot.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.himanshu.poc.springboot.web.controller"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}