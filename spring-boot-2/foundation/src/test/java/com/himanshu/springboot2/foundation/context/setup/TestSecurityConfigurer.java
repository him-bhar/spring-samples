package com.himanshu.springboot2.foundation.context.setup;

import com.himanshu.springboot2.foundation.security.BaseSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestSecurityConfigurer extends BaseSecurityConfigurer {

  public TestSecurityConfigurer() {
    super("(.*?)");
  }

  @Bean
  public TestController testController() {
    return new TestController();
  }
}
