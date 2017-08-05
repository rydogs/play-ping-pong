package com.offdk.play.model.slack;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class User implements Identifiable {
  private final String id;
  private final String name;

  @JsonCreator
  public User(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  // return slack user format
  @Override
  public String toString() {
    return "<@" + getId() + "|" + getName() + ">";
  }

  @Override
  public boolean equals(Object o){
    // self check
    if (this == o)
      return true;
    // null check
    if (o == null)
      return false;
    // type check and cast
    if (getClass() != o.getClass())
      return false;
    User myUser = (User) o;
    // field comparison
    return Objects.equals(getId(), myUser.getId())
        && Objects.equals(getName(), myUser.getName());
  }
}