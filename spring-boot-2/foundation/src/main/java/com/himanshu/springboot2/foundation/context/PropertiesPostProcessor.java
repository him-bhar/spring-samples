package com.himanshu.springboot2.foundation.context;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//This annotation added to ensure encrypted properties can be decrypted.
@EnableEncryptableProperties
public class PropertiesPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

  private static Logger logger = LoggerFactory.getLogger(PropertiesPostProcessor.class);

  private ResourceLoader resourceLoader;
  private final String applicationName;

  public PropertiesPostProcessor(String applicationName) {
    this.resourceLoader = new DefaultResourceLoader();
    this.applicationName = applicationName;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    String envProperty = System.getProperty("env");
    if (envProperty == null || "".equalsIgnoreCase(envProperty.trim())) {
      envProperty = "local";
      logger.warn("[DEFAULT] Loading file paths for env:[{}]", envProperty);
    }
    logger.info("Loading file paths for env:[{}]", envProperty);
    StandardEnvironment env = (StandardEnvironment) configurableListableBeanFactory.getBean("environment");
    MutablePropertySources propertySources = env.getPropertySources();
    for (String filePath : filePaths(envProperty)) {
      Resource res = resourceLoader.getResource(filePath);
      logger.info("res {}", res);
      if (res.exists() && res != null) {
        try {
          Properties props = PropertiesLoaderUtils.loadProperties(res);
          PropertiesPropertySource source = new PropertiesPropertySource(res.getFilename(), props);
          propertySources.addFirst(source);
          postProcessProperties(source);
        } catch (IOException e) {
          logger.error("Error loading file path : {}", res.getDescription(), e);
        }
      }
    }
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
    filePaths.add("config/global.default.properties");
    filePaths.add(String.format("config/%s.%s.properties", applicationName, envProperty));
    filePaths.add("config/personal.properties");
    return filePaths;
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
   * Setting priority of this Spring Bean processor
   * @return
   */
  @Override
  public int getOrder() {
    return PriorityOrdered.HIGHEST_PRECEDENCE;
  }
}
