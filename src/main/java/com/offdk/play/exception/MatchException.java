package com.offdk.play.exception;

public class MatchException extends RuntimeException {
  MatchException(String message) {
    super(message);
  }

  public static MatchException of(String message) {
    return new MatchException(message);
  }
}
