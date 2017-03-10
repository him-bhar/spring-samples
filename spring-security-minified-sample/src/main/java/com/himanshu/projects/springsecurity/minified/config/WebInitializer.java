package com.himanshu.projects.springsecurity.minified.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext paramServletContext) throws ServletException {
    
    //First define the spring context, for the webapp
    AbstractRefreshableWebApplicationContext dispatcherContext = getSpringDispatcherUsingAnnotations();
    //Register the spring context with ContextLoaderListener, so can be used with webapp
    paramServletContext.addListener(new ContextLoaderListener(dispatcherContext));
    
    //Mapping with Dispatcher Servlet and mapping all urls to this servlet
    ServletRegistration.Dynamic dispatcher = paramServletContext.addServlet("spring", new DispatcherServlet(dispatcherContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
    
    //Initializing Spring Security filter
    registerSpringSecurityFilter(paramServletContext);
  }
  
  private AnnotationConfigWebApplicationContext getSpringDispatcherUsingAnnotations() {
    AnnotationConfigWebApplicationContext annotationWebAppContext = new AnnotationConfigWebApplicationContext();
    annotationWebAppContext.register(ContextConfigLoader.class, WebSpringConfig.class);
    return annotationWebAppContext;
  }
  
  private void registerSpringSecurityFilter(ServletContext paramServletContext) {
    //Initializing Spring Security
    paramServletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
       .addMappingForUrlPatterns(null, false, "/*");
  }
  
  //No need for this
  @Deprecated
  private void registerCharacterEncodingFilter(ServletContext paramServletContext) {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);
    paramServletContext.addFilter("characterEncodingFilter", encodingFilter).addMappingForUrlPatterns(null, false, "/WEB-INF/templates/*");
  }
  
  
  //Using spring xml config files. Not required anymore as we use Annotations now.
  @Deprecated
  private XmlWebApplicationContext getSpringContextWithXmlFiles(ServletContext paramServletContext) {
    XmlWebApplicationContext context = new XmlWebApplicationContext();
    context.setConfigLocations("/WEB-INF/spring-servlet.xml", "classpath:/spring-db.xml", "classpath:/spring-service.xml", "classpath:/spring-security.xml");
    return context;
  }

}
