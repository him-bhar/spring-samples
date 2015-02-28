#Spring Security Integration with Spring Boot Securing Http Urls

In the current implementation here, there are two ways of authentication:

1. Authentication using username and password.
 1. See class _com.himanshu.poc.springbootsec.security.AuthenticationProviderImpl_ : This class's authenticate method contains the crux of the logic on how authentication is to be done. If username is supplied, then it's considered username and password authentication
 2. Also, see class _com.himanshu.poc.springbootsec.security.SecurityConfigurer_
 
2. Authentication using token.
 1. See class _com.himanshu.poc.springbootsec.security.AuthenticationProviderImpl_ : This class's authenticate method contains the crux of the logic on how authentication is to be done. If username is **NOT** supplied, then it's considered token-based authentication.
 2. Also, see class _com.himanshu.poc.springbootsec.security.SecurityConfigurer_
 3. To generate the token: Call,```java 
 /secure/generate/token/{name} 
 ``` 
 with right user-credentials. (Refer testcase: SampleControllerSecurityTestIT , Method: testSecureGetWithToken)