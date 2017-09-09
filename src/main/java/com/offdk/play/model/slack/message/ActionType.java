package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonValue;

enum ActionType {
  BUTTON("button"),
  SELECT("select");

  private final String value;

  ActionType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
