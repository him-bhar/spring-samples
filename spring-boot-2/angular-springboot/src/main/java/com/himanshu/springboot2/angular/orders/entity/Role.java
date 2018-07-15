package com.himanshu.springboot2.angular.orders.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "role_name")
  private String roleName;

  @ManyToMany(targetEntity = User.class, mappedBy = "roles", fetch = FetchType.EAGER)
  private List<User> users;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Role role = (Role) o;

    if (id != role.id) return false;
    if (roleName != null ? !roleName.equals(role.roleName) : role.roleName != null) return false;
    return users != null ? users.equals(role.users) : role.users == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
    result = 31 * result + (users != null ? users.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Role{" +
            "id=" + id +
            ", roleName='" + roleName + '\'' +
            //", users=" + users +
            '}';
  }
}
