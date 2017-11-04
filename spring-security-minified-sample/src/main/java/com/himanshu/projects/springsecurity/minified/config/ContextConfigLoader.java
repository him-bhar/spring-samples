/*
 * Copyright 2014 himanshu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.himanshu.projects.springsecurity.minified.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.spring31.properties.EncryptablePropertiesPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

//In order to load configuration as first thing in context initialization, 
//I have implemented PriorityOrdered and given Highest Priority to this bean. 
//This will be the first bean to run
/**
 * The Class ContextConfigLoader.
 */
@Configuration
public class ContextConfigLoader implements BeanFactoryPostProcessor, PriorityOrdered {
  
  private String password = "Optmi$ation";
  

  /** The resource loader. */
  private DefaultResourceLoader resourceLoader;
  
  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(ContextConfigLoader.class);
  
  private StandardPBEStringEncryptor encryptor;

  /**
   * Instantiates a new context config loader.
   */
  public ContextConfigLoader() {
    this.resourceLoader = new DefaultResourceLoader();
    encryptor = getEncryptor();
  }
  
  private StandardPBEStringEncryptor getEncryptor() {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(password);
    return encryptor;
  }

  /* (non-Javadoc)
   * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
   */
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    String envProperty = System.getProperty("env");
    logger.info("Load file paths for environment {}", envProperty);
    StandardEnvironment env = (StandardEnvironment)beanFactory.getBean("environment");
    MutablePropertySources propSources = env.getPropertySources();
    for (String filePath : filePaths(envProperty)) {
      Resource res = resourceLoader.getResource(filePath);
      logger.info("res {}", res);
      if (res.exists() && res != null) {
        try {
          Properties props = PropertiesLoaderUtils.loadProperties(res);
          PropertiesPropertySource source = new EncryptablePropertiesPropertySource(filePath, props, this.encryptor);
          propSources.addFirst(source);
          postProcessProperties(source);
        } catch (IOException e) {
          logger.error("Error loading file path : {}", res.getDescription(), e);
        }
      }
    }
  }

  /**
   * Post process properties.
   *
   * @param source the source
   */
  private void postProcessProperties(PropertiesPropertySource source) {
    for (String propertyName : source.getPropertyNames())
      logger.debug("Loaded Property: {} with value: {}", propertyName, source.getProperty(propertyName));
  }

  /**
   * File paths.
   *
   * @param envProperty the env property
   * @return the list
   */
  private List<String> filePaths(String envProperty) {
    List<String> filePaths = new ArrayList<String>();
    filePaths.add(String.format("config/global.default.properties", envProperty));
    filePaths.add(String.format("config/spring.%s.properties", envProperty));
    filePaths.add("config/local.default.properties");
    filePaths.add(String.format("config/personal.properties", envProperty));
    return filePaths;
  }

  /* (non-Javadoc)
   * @see org.springframework.core.Ordered#getOrder()
   */
  @Override
  public int getOrder() {
    return PriorityOrdered.HIGHEST_PRECEDENCE;
  }
  
  /**
   * Regaistering PropertySourcesPlaceholderConfigurer so placholders can be replaced by actual value
   * @return
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
