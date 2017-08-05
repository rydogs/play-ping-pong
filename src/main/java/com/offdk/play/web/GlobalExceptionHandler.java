package com.offdk.play.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.offdk.play.exception.MatchException;
import com.offdk.play.model.slack.request.Message;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(MatchException.class)
  @ResponseBody
  public Message handleMatchException(MatchException e) {
    return Message.createEphemeralMessage().text(e.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public Message handleException(Throwable e) {
    return Message.createEphemeralMessage().text("Panic:" + e.getMessage()).build();
  }
}
