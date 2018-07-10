package com.himanshu.springboot2.angular.orders.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String username;
  private String email;
  private String city;
  private String country;
  @OneToMany(targetEntity = Order.class, mappedBy = "customer", fetch = FetchType.EAGER)
  private List<Order> orders;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            //", orders=" + orders +
            '}';
  }
}
