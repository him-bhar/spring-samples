package com.himanshu.projects.springsecurity.minified.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.himanshu.projects.springsecurity.minified.http.converter.YamlHttpMessageConverter;

@EnableWebMvc
@ComponentScan(basePackages="com.himanshu.projects.springsecurity.minified.config")  
  //excludeFilters={@ComponentScan.Filter(type=FilterType.REGEX, pattern="com\\.himanshu\\.projects\\.springsecurity\\.minified\\.config\\..*")})
@Import(value={ContextConfigLoader.class})
@Configuration
public class WebSpringConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    
  private ApplicationContext applicationContext;
  
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(new MappingJackson2HttpMessageConverter());
    converters.add(new MappingJackson2XmlHttpMessageConverter());
    converters.add(new YamlHttpMessageConverter<>());
    super.configureMessageConverters(converters);
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    super.addResourceHandlers(registry);
    registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
    registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");
    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    super.configureDefaultServletHandling(configurer);
    configurer.enable();
  }
  
  
  @Override
  public void addFormatters(FormatterRegistry registry) {
    super.addFormatters(registry);
    registry.addFormatter(new DateFormatter());
  }
  
  @Bean
  public ITemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(applicationContext);
    templateResolver.setCacheable(false);
    templateResolver.setPrefix("/WEB-INF/layouts/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    return templateResolver;
  }
  
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setEnableSpringELCompiler(true);
    templateEngine.setTemplateResolver(templateResolver());
    
    Set<IDialect> additionalDialects = new HashSet<>();
    additionalDialects.add(new SpringSecurityDialect());
    templateEngine.setAdditionalDialects(additionalDialects);
    return templateEngine;
  }
  
  @Bean
  public ViewResolver thymeleafViewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");
    return viewResolver;
  }
  
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("message");
    return messageSource;
  }

  @Override
  public void setApplicationContext(ApplicationContext arg0) throws BeansException {
    this.applicationContext = arg0;
  }
  
}
