package com.offdk.play.model.slack.message;

enum ActionType {
  BUTTON,
  SELECT;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
