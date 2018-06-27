package com.himanshu.springboot2.angular.config;

import com.himanshu.springboot2.angular.orders.dao.CustomerDao;
import com.himanshu.springboot2.angular.orders.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(value="orderPU",
        basePackageClasses = CustomerDao.class,
        entityManagerFactoryRef = "orderEntityManagerFactory",
        transactionManagerRef = "sampleDSTransactionManager")
public class JpaRepositoryConfigurer {

  @Bean(name = "orderEntityManagerFactory")
  public EntityManagerFactory entityManagerFactory(@Qualifier("sampleDS") DataSource ds) {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(false);
    vendorAdapter.setShowSql(true);

    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(ds);
    lef.setJpaVendorAdapter(vendorAdapter);
    lef.setPackagesToScan(Customer.class.getPackage().getName());
    lef.setPersistenceUnitName("orderPU");
    lef.afterPropertiesSet();
    return lef.getObject();
  }
}
