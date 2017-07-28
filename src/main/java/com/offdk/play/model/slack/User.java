package com.offdk.play.model.slack;

import com.google.common.base.MoreObjects;

public class User {
  private final String userId;
  private final String userName;

  public User(String userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  public String getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String toString() {
    return MoreObjects.toStringHelper(this.getClass()).add("id", userId).add("username", userName).toString();
  }
}
