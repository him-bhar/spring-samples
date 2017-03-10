package com.himanshu.projects.springsecurity.minified.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Conditional(value=DBServerCondition.class)
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.himanshu.projects.springsecurity",  
  excludeFilters={@ComponentScan.Filter(type=FilterType.REGEX, pattern="com\\.himanshu\\.projects\\.springsecurity\\.minified\\.config\\..*")})
@Import(value={ContextConfigLoader.class})
public class DBConfig {
  
  private static Logger logger = LoggerFactory.getLogger(DBConfig.class);
  
  @Value("${jdbc.db_url}")
  private String dbUrl;
  
  @Value("${jdbc.username}")
  private String dbUsername;
  
  @Value("${jdbc.password}")
  private String dbPassword;
  
  @Value("${jdbc.minPoolSize}")
  private int minPoolSize;
  
  @Value("${jdbc.maxPoolSize}")
  private int maxPoolSize;
  
  @Value("${jdbc.testConnection}")
  private boolean testConnection;
  
  @Value("${jdbc.driverClassName}")
  private String driverClassName;
  
  @Conditional(value=DBServerCondition.class)
  @Bean
  @Qualifier("dataSource")
  public DataSource getDataSource() throws PropertyVetoException {
    logger.info("Instantiating from actual DB server");
    ComboPooledDataSource c3p0DataSource = new ComboPooledDataSource();
    c3p0DataSource.setDriverClass(driverClassName);
    c3p0DataSource.setJdbcUrl(dbUrl);
    c3p0DataSource.setUser(dbUsername);
    c3p0DataSource.setPassword(dbPassword);
    c3p0DataSource.setMinPoolSize(minPoolSize);
    c3p0DataSource.setMaxPoolSize(maxPoolSize);
    c3p0DataSource.setTestConnectionOnCheckout(testConnection);
    return c3p0DataSource;
  }
  
  @Conditional(value=DBServerCondition.class)
  @Bean
  public PlatformTransactionManager transactionManager() throws PropertyVetoException {
    DataSourceTransactionManager dsTransactionManager = new DataSourceTransactionManager(getDataSource());
    return dsTransactionManager;
  }
}
