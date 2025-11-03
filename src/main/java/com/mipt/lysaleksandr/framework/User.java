package com.mipt.lysaleksandr.framework;

public class User {
  @NotNull(message = "The name cannot be null")
  @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
  private String name;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Incorrect format email")
  private String email;

  @Range(min = 0, max = 6000, message = "Age must be between 0 and 6000")
  private Long age;

  @Size(min = 6, max = 20, message = "The password must be between 6 and 20 characters long")
  private String password;

  public User(String name, String email, Long age, String password) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.password = password;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public Long getAge() {
    return age;
  }
  public void setAge(Long age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}