package com.offdk.play.model.slack;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Channel implements Identifiable {
  private final String id;
  private final String name;

  @JsonCreator
  public Channel(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
}
