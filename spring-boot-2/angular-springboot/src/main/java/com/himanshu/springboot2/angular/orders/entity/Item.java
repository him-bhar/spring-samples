package com.himanshu.springboot2.angular.orders.entity;

import javax.persistence.*;

@Entity
@Table(name="items")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String description;
  private Long inventory;
  private Double price;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getInventory() {
    return inventory;
  }

  public void setInventory(Long inventory) {
    this.inventory = inventory;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", inventory=" + inventory +
            ", price=" + price +
            '}';
  }
}
