package com.m2p.fileprocessor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

//  @NotNull
  private String name;

  // longmillisec
  private Integer dob;

  private Integer age;

//  @NotNull
  private Integer mobileNumber;

  public User() {}

  public User( String name) {
    this.name = name;
    this.dob = dob;
    this.age = age;
    this.mobileNumber = mobileNumber;
  }
}
