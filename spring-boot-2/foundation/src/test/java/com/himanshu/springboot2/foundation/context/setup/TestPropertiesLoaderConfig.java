package com.himanshu.springboot2.foundation.context.setup;

import com.himanshu.springboot2.foundation.context.PropertiesPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestPropertiesLoaderConfig extends PropertiesPostProcessor {

  public TestPropertiesLoaderConfig() {
    super("sample-app");
  }

  @Bean
  public DummyObject getDummyObject() {
    return new DummyObject("sample bean String");
  }
}
