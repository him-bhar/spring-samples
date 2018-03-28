package com.himanshu.springboot2.foundation.context;

import com.himanshu.springboot2.foundation.context.setup.TestPropertiesLoaderConfig;
import com.himanshu.springboot2.foundation.context.setup.TestSecurityConfigurer;
import com.himanshu.springboot2.foundation.security.SecurityBeansConfigurer;
import com.himanshu.springboot2.foundation.security.config.DBConfig;
import com.himanshu.springboot2.foundation.security.config.InMemDBConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = {TestPropertiesLoaderConfig.class, TestSecurityConfigurer.class, SecurityBeansConfigurer.class, InMemDBConfig.class, DBConfig.class})
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class ApiSecurityTest {

  @BeforeAll
  public static void setJasyptPassword() {
    System.setProperty("jasypt.encryptor.password", "s3cr3t");
  }

  @BeforeAll
  public static void setInMemDBUsage() {
    System.setProperty("jdbc.inmem.db", "true");
  }
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
  }

  @Test
  @DisplayName("Should run controller successfully, providing security with credentials and right role")
  void shouldThrowUnauthenticatedExceptionWhenRightAuthority() throws Exception {
    setupCredentialsAndAuthority("ROLE_ADMIN");
    MvcResult result = this.mockMvc
            .perform(MockMvcRequestBuilders.get("/hello"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    Assertions.assertAll(() -> Assertions.assertEquals("hello world", result.getResponse().getContentAsString()));
  }

  @Test
  @DisplayName("Testing controller security with credentials, but different role")
  void shouldThrowUnauthenticatedExceptionWhenDifferentAuthorityCredentials() throws Exception {
    setupCredentialsAndAuthority("ROLE_USER");
    this.mockMvc
            .perform(MockMvcRequestBuilders.get("/hello"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @DisplayName("Testing controller security without credentials")
  void shouldThrowUnauthenticatedExceptionWhenNoCredentials() throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.get("/hello"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @AfterEach
  void cleanupSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  private void setupCredentialsAndAuthority(String role) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    Authentication auth = new UsernamePasswordAuthenticationToken("Mr. Big", "password", authorities);

    SecurityContextImpl securityContext = new SecurityContextImpl();
    securityContext.setAuthentication(auth);
    SecurityContextHolder.setContext(securityContext);
  }
}
