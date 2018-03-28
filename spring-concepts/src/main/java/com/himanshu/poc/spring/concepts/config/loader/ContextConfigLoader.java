package com.himanshu.poc.spring.concepts.config.loader;

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
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ContextConfigLoader implements BeanFactoryPostProcessor, PriorityOrdered {

  private static Logger logger = LoggerFactory.getLogger(ContextConfigLoader.class);

  private ResourceLoader resourceLoader;

  private String password = "test-password";

  private StandardPBEStringEncryptor encryptor;

  public ContextConfigLoader() {
    resourceLoader = new DefaultResourceLoader();
    encryptor = getEncryptor();
  }

  private StandardPBEStringEncryptor getEncryptor() {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(password);
    return encryptor;
  }

  @Override
  public int getOrder() {
    return PriorityOrdered.HIGHEST_PRECEDENCE;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    String envProperty = System.getProperty("env");
    logger.info("Load file paths for environment {}", envProperty);
    StandardEnvironment env = (StandardEnvironment) beanFactory.getBean("environment");
    MutablePropertySources propertySources = env.getPropertySources();
    for (String filePath : filePaths(envProperty)) {
      Resource res = resourceLoader.getResource(filePath);
      logger.info("res {}", res);
      if (res.exists() && res != null) {
        try {
          Properties props = PropertiesLoaderUtils.loadProperties(res);
          PropertiesPropertySource source = new EncryptablePropertiesPropertySource(filePath, props, this.encryptor);
          propertySources.addFirst(source);
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
   * @param source
   *          the source
   */
  private void postProcessProperties(PropertiesPropertySource source) {
    for (String propertyName : source.getPropertyNames())
      logger.debug("Loaded Property: {} with value: {}", propertyName, source.getProperty(propertyName));
  }

  /**
   * File paths.
   *
   * @param envProperty
   *          the env property
   * @return the list
   */
  private List<String> filePaths(String envProperty) {
    List<String> filePaths = new ArrayList<String>();
    filePaths.add(String.format("config/global.default.properties", envProperty));
    filePaths.add(String.format("config/spring.%s.properties", envProperty));
    filePaths.add(String.format("config/personal.properties", envProperty));
    return filePaths;
  }

  /**
   * This is to enable property resolution for place-holders
   * 
   * @return
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
