package com.himanshu.projects.springsecurity.minified.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private ControllerApiTaskFactory taskFactory;

  @RequestMapping(value = {"/echo"}, produces = {"application/xml", "application/json", "application/yaml"})
  public @ResponseBody Welcome contactUsPage(Model model) {
    logger.info("API msg");
    return new Welcome("Hello World");
  }

  @RequestMapping(value = {"deferredecho"}, produces = {"application/xml", "application/json", "application/yaml"})
  public @ResponseBody DeferredResult<Welcome> deferredEcho(Model model) {
    logger.info("Called deferredEcho");
    ApiControllerTask task = taskFactory.createEchoTask();
    taskFactory.submit(task);
    return task.getResult();
  }

}