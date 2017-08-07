package com.offdk.play.exception;

import java.util.function.Consumer;

public class MatchException extends RuntimeException {
  MatchException(String message) {
    super(message);
  }

  public static MatchException of(String message) {
    return new MatchException(message);
  }

  public static Consumer<Throwable> ofThrow(String message) {
    return t -> {
      throw MatchException.of(message);
    };
  }
}
