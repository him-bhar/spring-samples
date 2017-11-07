package com.himanshu.spring.eureka.samples.controller;

public class Fruit {
  private final String name;
  private final String color;
  private final boolean withPeel;

  public Fruit(String name, String color, boolean withPeel) {
    this.name = name;
    this.color = color;
    this.withPeel = withPeel;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color;
  }

  public boolean isWithPeel() {
    return withPeel;
  }

  @Override
  public String toString() {
    return "Fruit{" +
            "name='" + name + '\'' +
            ", color='" + color + '\'' +
            ", withPeel=" + withPeel +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Fruit fruit = (Fruit) o;

    if (withPeel != fruit.withPeel) return false;
    if (name != null ? !name.equals(fruit.name) : fruit.name != null) return false;
    return color != null ? color.equals(fruit.color) : fruit.color == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (color != null ? color.hashCode() : 0);
    result = 31 * result + (withPeel ? 1 : 0);
    return result;
  }
}
