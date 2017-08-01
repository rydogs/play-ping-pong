package com.offdk.play.model.slack;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Team implements Identifiable {
  private final String id;
  private final String domain;

  @JsonCreator
  public Team(String id, String domain) {
    this.id = id;
    this.domain = domain;
  }

  public String getId() {
    return id;
  }

  public String getDomain() {
    return domain;
  }
}
