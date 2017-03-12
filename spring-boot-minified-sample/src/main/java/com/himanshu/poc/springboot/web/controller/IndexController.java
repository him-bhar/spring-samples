package com.himanshu.poc.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    String index() {
        System.out.println("IndexController3");
        return "hello";
    }
}
