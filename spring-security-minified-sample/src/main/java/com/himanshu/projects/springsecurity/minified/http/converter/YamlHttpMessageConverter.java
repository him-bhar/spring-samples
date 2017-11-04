package com.himanshu.projects.springsecurity.minified.http.converter;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.himanshu.projects.springsecurity.minified.config.WebSpringConfig;

/**
 * Http Message Converter written to support application/yaml content-type or accepts header in http request.
 * Need to register the same in SpringWeb as an additional messageConverter.
 * @see WebSpringConfig#configureMessageConverters(java.util.List)
 * @see WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
 * @author himanshu
 *
 * @param <T>
 */
public class YamlHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {
  
  public YamlHttpMessageConverter() {
    super(new MediaType("application", "yaml"));
  }

  @Override
  protected T readInternal(Class<? extends T> arg0, HttpInputMessage arg1) throws IOException, HttpMessageNotReadableException {
    Yaml yaml = new Yaml(new Constructor(arg0));
    T t = (T)yaml.load(arg1.getBody());
    return t;
  }

  @Override
  protected boolean supports(Class<?> arg0) {
    return true; //Essentially no checks required any input or output object can be tried to be converted to Yaml string
  }

  @Override
  protected void writeInternal(T arg0, HttpOutputMessage arg1) throws IOException, HttpMessageNotWritableException {
    Yaml yaml = new Yaml();
    String yamlEquivalent = yaml.dump(arg0);
    arg1.getBody().write(yamlEquivalent.getBytes());
  }

}
