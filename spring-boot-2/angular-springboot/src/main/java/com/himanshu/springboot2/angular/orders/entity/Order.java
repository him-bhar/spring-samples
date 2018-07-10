package com.himanshu.springboot2.angular.orders.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
  @JoinColumn(referencedColumnName = "id")
  private Customer customer;

  private Double price;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", customer=" + customer +
            ", price=" + price +
            '}';
  }
}
