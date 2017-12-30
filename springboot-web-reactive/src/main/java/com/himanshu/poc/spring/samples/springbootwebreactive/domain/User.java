package com.himanshu.poc.spring.samples.springbootwebreactive.domain;

import javax.persistence.*;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="username")
  private String name;

  @Column(name="password")
  private String pwd;

  @Column(name="email")
  private String email;

  @Column(name="nonlocked")
  private boolean isUnlocked;

  @Column(name="nonexpired")
  private boolean isValid;

  @Column(name="credentialsnonexpired")
  private boolean isCredentialsValid;

  private boolean enabled;

  public User() {
  }

  public User(Long id, String name, String pwd, String email, boolean isUnlocked, boolean isValid, boolean isCredentialsValid, boolean enabled) {
    this.id = id;
    this.name = name;
    this.pwd = pwd;
    this.email = email;
    this.isUnlocked = isUnlocked;
    this.isValid = isValid;
    this.isCredentialsValid = isCredentialsValid;
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isUnlocked() {
    return isUnlocked;
  }

  public void setUnlocked(boolean unlocked) {
    isUnlocked = unlocked;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public boolean isCredentialsValid() {
    return isCredentialsValid;
  }

  public void setCredentialsValid(boolean credentialsValid) {
    isCredentialsValid = credentialsValid;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (isUnlocked != user.isUnlocked) return false;
    if (isValid != user.isValid) return false;
    if (isCredentialsValid != user.isCredentialsValid) return false;
    if (enabled != user.enabled) return false;
    if (id != null ? !id.equals(user.id) : user.id != null) return false;
    if (name != null ? !name.equals(user.name) : user.name != null) return false;
    if (pwd != null ? !pwd.equals(user.pwd) : user.pwd != null) return false;
    return email != null ? email.equals(user.email) : user.email == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (isUnlocked ? 1 : 0);
    result = 31 * result + (isValid ? 1 : 0);
    result = 31 * result + (isCredentialsValid ? 1 : 0);
    result = 31 * result + (enabled ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", pwd='" + pwd + '\'' +
            ", email='" + email + '\'' +
            ", isUnlocked=" + isUnlocked +
            ", isValid=" + isValid +
            ", isCredentialsValid=" + isCredentialsValid +
            ", enabled=" + enabled +
            '}';
  }
}
