package com.himanshu.projects.springsecurity.minified.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class InMemDBCondition implements Condition {

  @Override
  public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
    String inMemDbRequired = System.getProperty("jdbc.inmem.db");
    if (inMemDbRequired != null && "true".equalsIgnoreCase(inMemDbRequired)) {
      return true;
    } else {
      return false;
    }
  }

}
