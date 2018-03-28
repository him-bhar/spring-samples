package com.himanshu.springboot2.foundation.context;

import com.himanshu.springboot2.foundation.context.setup.DummyObject;
import com.himanshu.springboot2.foundation.context.setup.TestPropertiesLoaderConfig;
import com.himanshu.springboot2.foundation.context.setup.TestSecurityConfigurer;
import com.himanshu.springboot2.foundation.security.SecurityBeansConfigurer;
import com.himanshu.springboot2.foundation.security.config.DBConfig;
import com.himanshu.springboot2.foundation.security.config.InMemDBConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestPropertiesLoaderConfig.class, TestSecurityConfigurer.class, SecurityBeansConfigurer.class, InMemDBConfig.class, DBConfig.class})
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class PropertiesPostProcessorTest {
  @BeforeAll
  public static void setJasyptPassword() {
    System.setProperty("jasypt.encryptor.password", "s3cr3t");
  }

  @BeforeAll
  public static void setInMemDBUsage() {
    System.setProperty("jdbc.inmem.db", "true");
  }

  private static Logger logger = LoggerFactory.getLogger(PropertiesPostProcessorTest.class);

  @Autowired
  private DummyObject dummyObject;

  @Value("${sample.encrypted.value}")
  private String encryptedValue;


  @Test
  public void testContextLoad() {
    Assertions.assertNotNull(dummyObject);
  }

  @Test
  @DisplayName("Is encryption working using jasypt")
  public void testEncryptionWorking() {
    Assertions.assertAll(() -> Assertions.assertNotNull(encryptedValue),
            () -> Assertions.assertEquals("my test value", encryptedValue));
  }

}
