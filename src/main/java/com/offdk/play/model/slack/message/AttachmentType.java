package com.offdk.play.model.slack.message;

public enum AttachmentType {
  DEFAULT;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
