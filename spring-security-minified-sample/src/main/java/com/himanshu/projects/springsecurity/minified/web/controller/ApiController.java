package com.himanshu.projects.springsecurity.minified.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = { "/echo" }, produces = { "application/xml", "application/json", "application/yaml" })
    public @ResponseBody Welcome contactUsPage(Model model) {
        logger.info("API msg");
        return new Welcome("Hello World");
    }

}