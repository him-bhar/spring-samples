package com.himanshu.poc.springbootsec.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.himanshu.poc.springbootsec.dao.DummyTblDao;
import com.himanshu.poc.springbootsec.dao.PersonDao;

/**
 * Configuration commented just for the sake that I want to run the WebStarter not command line
 * @author himanshu
 *
 */
//@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.himanshu.poc.springbootsec"})
public class SampleCommandLineStarter implements CommandLineRunner {
  
  private Logger logger  = LoggerFactory.getLogger(getClass());

  @Autowired
  DummyTblDao dummyTblDao;

  @Autowired
  PersonDao personDao;

  public static void main(String[] args) {
    SpringApplication.run(SampleCommandLineStarter.class, args);
  }

  public void run(String... args) {
    logger.info(personDao.getNames().toString());
    logger.info(dummyTblDao.getNames().toString());
  }
}
