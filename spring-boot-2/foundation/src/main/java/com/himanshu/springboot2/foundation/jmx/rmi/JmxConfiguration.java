package com.himanshu.springboot2.foundation.jmx.rmi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnectorServer;
import javax.security.auth.Subject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public abstract class JmxConfiguration {

  @Value("${jmx.rmi.host:localhost}")
  private String rmiHost;

  @Value("${jmx.rmi.port:1099}")
  private Integer rmiPort;

  @Bean
  public RmiRegistryFactoryBean rmiRegistry() {
    final RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
    rmiRegistryFactoryBean.setPort(rmiPort);
    rmiRegistryFactoryBean.setAlwaysCreate(true);
    return rmiRegistryFactoryBean;
  }

  @Bean
  @DependsOn("rmiRegistry")
  public ConnectorServerFactoryBean connectorServerFactoryBean(AuthenticationManager authenticationManager) throws Exception {
    final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
    Map<String, Object> envProperties = new HashMap<>();
    envProperties.put(JMXConnectorServer.AUTHENTICATOR, new JMXAuthenticator() {
      public Subject authenticate(Object credentials) {
        System.out.println(credentials);
        UsernamePasswordAuthenticationToken jmxToken = new UsernamePasswordAuthenticationToken(((String[])credentials)[0], ((String[])credentials)[1]);
        authenticationManager.authenticate(jmxToken);
        return new Subject(false, new HashSet<>(), new HashSet<Object>(), new HashSet<Object>());
      }
    });
    connectorServerFactoryBean.setEnvironmentMap(envProperties);
    connectorServerFactoryBean.setObjectName("connector:name=rmi");
    connectorServerFactoryBean.setServiceUrl(String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi", rmiHost, rmiPort, rmiHost, rmiPort));
    return connectorServerFactoryBean;
  }
}
