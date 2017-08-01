package com.offdk.play.model.slack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

public class User implements Identifiable {
  private final String id;
  private final String name;

  @JsonCreator
  public User(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return MoreObjects.toStringHelper(this.getClass()).add("id", id).add("name", name).toString();
  }
}