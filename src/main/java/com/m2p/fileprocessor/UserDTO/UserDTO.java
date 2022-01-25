package com.m2p.fileprocessor.UserDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.logging.Logger;

public class UserDTO implements Serializable {

  private static Logger log = Logger.getLogger(UserDTO.class.getSimpleName());

  @JsonInclude(value = Include.NON_EMPTY)
  private int id;

  @JsonInclude(value = Include.NON_EMPTY)
  private String name;

  @JsonInclude(value = Include.NON_EMPTY)
  private String dob;

  @JsonInclude(value = Include.NON_EMPTY)
  private long age;

  @JsonInclude(value = Include.NON_EMPTY)
  private long mobileNumber;

  public static UserDTO setDTO(int id, String name, String dob, long age, long mobileNumber) {
    UserDTO userDTO = new UserDTO();
    try {
      userDTO.setName(name);
      userDTO.setAge(age);
      userDTO.setDob(dob);
      userDTO.setId(id);
      userDTO.setMobileNumber(mobileNumber);
    } catch (Exception e) {
      e.printStackTrace();
      log.severe(e.getMessage());
    }

    return userDTO;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }

  public long getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(long mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (Exception e) {
      return null;
    }
  }
}
