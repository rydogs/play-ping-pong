package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonValue;

enum ResponseType {
  IN_CHANNEL("in_channel"),
  EPHEMERAL("ephemeral");

  private final String value;

  ResponseType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
