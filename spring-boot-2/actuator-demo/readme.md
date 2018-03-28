Created a secured a JMX layer, that can be connected via JVisualVM. The security is based on spring-security itself.
```service:jmx:rmi://localhost:1099/jndi/rmi://localhost:1099/jmxrmi```

See class, 
1. com.himanshu.springboot2.actuator.config.JMXConfigurer
1. com.himanshu.springboot2.foundation.jmx.rmi.JmxConfiguration

JMXConfiguration contains the actual code on how JMX connections been made secured.

Also, exposed JMX via jolokia and consumed it on hawtio.

```java -Dhawtio.authenticationEnabled=false  -jar hawtio-app-1.5.8.jar --port 8090```

Need to ensure that ```/actuator, /actuator/jolokia and actuator/jolokia/**``` are all non-secured explicitly. Made changes in BaseSecurityConfigurer (foundation) to ensure its propagated to all services seamlessly. 