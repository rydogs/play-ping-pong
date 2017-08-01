package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StyleType {
  DEFAULT("default"),
  PRIMARY("primary"),
  DANGER("danger");

  private final String value;

  StyleType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
