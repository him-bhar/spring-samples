package com.himanshu.springboot2.foundation.security.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Conditional(value=DBServerCondition.class)
@Configuration
@EnableTransactionManagement
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

  @Bean(name = "securityDS")
  public DataSource getDataSource() throws PropertyVetoException {
    logger.info("Instantiating from actual DB server");
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(driverClassName);
    hikariConfig.setJdbcUrl(dbUrl);
    hikariConfig.setUsername(dbUsername);
    hikariConfig.setPassword(dbPassword);
    hikariConfig.setMaximumPoolSize(minPoolSize);
    hikariConfig.setMaximumPoolSize(maxPoolSize);
    HikariDataSource ds = new HikariDataSource(hikariConfig);
    return ds;
  }

  /**
   * Since, we don't use JPA here, hence DataSourceTransactionManager is sufficient
   */
  @Bean(name="securityTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("securityDS") DataSource dataSource) throws PropertyVetoException {
    DataSourceTransactionManager dsTransactionManager = new DataSourceTransactionManager(getDataSource());
    return dsTransactionManager;
  }
}
