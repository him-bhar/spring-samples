package com.himanshu.springboot2.foundation.context.setup;

public class DummyObject {
  private final String identifier;

  public DummyObject(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return "DummyObject{" +
            "identifier='" + identifier + '\'' +
            '}';
  }
}
