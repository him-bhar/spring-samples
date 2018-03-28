package com.himanshu.springboot2.foundation.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Conditional(value=InMemDBCondition.class)
@Configuration
@EnableTransactionManagement
public class InMemDBConfig {
  
  private static Logger logger = LoggerFactory.getLogger(InMemDBConfig.class);
  
  @Conditional(value=InMemDBCondition.class)
  @Bean
  public DataSource getDataSource() throws PropertyVetoException {
    logger.info("Instantiating InMem DBServer");
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL);
    embeddedDatabaseBuilder.addScript("classpath:/schema/schema.sql");
    embeddedDatabaseBuilder.setSeparator(";");
    return embeddedDatabaseBuilder.build();
  }
  
  @Conditional(value=InMemDBCondition.class)
  @Bean
  public PlatformTransactionManager transactionManager() throws PropertyVetoException {
    DataSourceTransactionManager dsTransactionManager = new DataSourceTransactionManager(getDataSource());
    return dsTransactionManager;
  }
}
