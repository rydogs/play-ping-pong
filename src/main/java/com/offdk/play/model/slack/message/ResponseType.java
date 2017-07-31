package com.offdk.play.model.slack.message;

enum ResponseType {
  IN_CHANNEL,
  EPHEMERAL;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
