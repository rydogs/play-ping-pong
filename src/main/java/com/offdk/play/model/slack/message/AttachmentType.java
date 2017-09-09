package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AttachmentType {
  DEFAULT("default");

  private final String value;

  AttachmentType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
