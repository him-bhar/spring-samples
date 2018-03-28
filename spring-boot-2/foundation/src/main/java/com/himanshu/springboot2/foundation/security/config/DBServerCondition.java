package com.himanshu.springboot2.foundation.security.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DBServerCondition implements Condition {

  @Override
  public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
    String inMemDbRequired = System.getProperty("jdbc.inmem.db");
    if (inMemDbRequired == null || "false".equalsIgnoreCase(inMemDbRequired)) {
      return true;
    } else {
      return false;
    }
  }

}
