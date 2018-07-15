package com.himanshu.springboot2.angular.orders.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "user_name")
  private String userName;

  @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @JoinTable(name="user_role_mapping", joinColumns = {
          @JoinColumn(name = "user_id")
  },
          inverseJoinColumns = {
                  @JoinColumn(name = "role_id")
          }

  )
  private List<Role> roles;

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", roles=" + roles +
            '}';
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    return userName != null ? userName.equals(user.userName) : user.userName == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    return result;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
