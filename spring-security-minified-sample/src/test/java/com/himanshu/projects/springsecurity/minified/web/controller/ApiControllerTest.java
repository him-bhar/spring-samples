package com.himanshu.projects.springsecurity.minified.web.controller;


import javax.annotation.Resource;

import com.himanshu.projects.springsecurity.minified.domain.Role;
import com.himanshu.projects.springsecurity.minified.domain.User;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.himanshu.projects.springsecurity.minified.config.InMemDBConfig;
import com.himanshu.projects.springsecurity.minified.config.SecurityConfigurer;
import com.himanshu.projects.springsecurity.minified.config.WebSpringConfig;

import java.util.ArrayList;
import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//Added web config and security config too
@ContextConfiguration(classes = { WebSpringConfig.class, InMemDBConfig.class, SecurityConfigurer.class })
public class ApiControllerTest {
  
  /**
   * When running test cases ensure, you run setting these system properties. It will run in in-mem db:
   * -Denv=dev 
   * -Djdbc.inmem.db=true
   */
  
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private MockHttpSession session;
  
  @Autowired
  private MockHttpServletRequest request;
  
  //This is to inject spring security filter chain
  @Resource
  private FilterChainProxy springSecurityFilterChain;

  private static Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);
  
  private MockMvc mockMvc;
  
  
  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.wac)
        //Adding spring security filters here
        .addFilters(springSecurityFilterChain)
        .build();
  }

  @Test
  public void testApiWithoutAuthentication() throws Exception {
    //reponse code is 302 because request is redirected to login page
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/echo").accept(MediaType.APPLICATION_XML))
      .andExpect(MockMvcResultMatchers.status().is(302)).andReturn();
  }
  
  @Test
  public void testApiWithAuthenticationJsonResponse() throws Exception {
    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("himanshu:bhardwaj").getBytes()));
    
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/echo")
      .header("Authorization", basicDigestHeaderValue)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    logger.info(mvcResult.getResponse().getContentAsString());
  }
  
  @Test
  public void testApiWithAuthenticationXmlResponse() throws Exception {
    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("himanshu:bhardwaj").getBytes()));
    
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/echo")
      .header("Authorization", basicDigestHeaderValue)
      .accept(MediaType.APPLICATION_XML))
      .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    logger.info(mvcResult.getResponse().getContentAsString());
  }
  
  @Test
  public void testApiWithAuthenticationYamlResponse() throws Exception {
    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("himanshu:bhardwaj").getBytes()));
    
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/echo")
      .header("Authorization", basicDigestHeaderValue)
      .accept("application/yaml"))
      .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    logger.info(mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void testDeferredApiWithAuthenticationXmlResponse() throws Exception {
    String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("himanshu:bhardwaj").getBytes()));
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/deferredecho")
            .header("Authorization", basicDigestHeaderValue)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .session(session))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andExpect(MockMvcResultMatchers.request().asyncResult(new Welcome("Pranam")))
            .andReturn();
    mockMvc
            .perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

    logger.info(mvcResult.getResponse().getContentAsString());
  }

  @Ignore("This would require me to remove spring security filterchain. Doesn't make much sense")
  @Test
  public void testDeferredApiWithAuthenticationXmlResponseWithMockedAuth() throws Exception {
    setupCredentials();
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/deferredecho")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .session(session))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.request().asyncStarted())
        .andExpect(MockMvcResultMatchers.request().asyncResult(new Welcome("Pranam")))
        .andReturn();
    mockMvc
        .perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

    logger.info(mvcResult.getResponse().getContentAsString());
  }


  private void setupCredentials() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    Authentication auth = new UsernamePasswordAuthenticationToken("Mr. Big", "password", authorities);
    //SecurityContextHolder.getContext().setAuthentication(auth);

    SecurityContextImpl securityContext = new SecurityContextImpl();
    securityContext.setAuthentication(auth);
    SecurityContextHolder.setContext(securityContext);
    //session.setAttribute("SPRING_SECURITY_CONTEXT", auth);
  }

}
