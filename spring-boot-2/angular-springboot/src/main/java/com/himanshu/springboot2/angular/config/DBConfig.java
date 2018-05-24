package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.foundation.security.config.InMemDBCondition;
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


@Configuration
@EnableTransactionManagement
public class DBConfig {
  private static Logger logger = LoggerFactory.getLogger(DBConfig.class);

  @Conditional(value=InMemDBCondition.class)
  @Bean(name = "sampleDS")
  public DataSource getDataSource() throws PropertyVetoException {
    logger.info("Instantiating InMem DBServer for sample DB");
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL);
    embeddedDatabaseBuilder.setName("sample_database");
    embeddedDatabaseBuilder.addScript("classpath:/schema/schema_sample.sql");
    embeddedDatabaseBuilder.setSeparator(";");
    return embeddedDatabaseBuilder.build();
  }
  
  @Bean(name="sampleDSTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("sampleDS") DataSource dataSource) throws PropertyVetoException {
    DataSourceTransactionManager dsTransactionManager = new DataSourceTransactionManager(dataSource);
    return dsTransactionManager;
  }
}
