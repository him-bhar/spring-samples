package com.himanshu.poc.spring.samples.springbootwebreactive.configurer;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class InMemDBCondition implements Condition {
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
    return Boolean.parseBoolean(conditionContext.getEnvironment().getProperty("is.in-mem.db"));
  }
}
