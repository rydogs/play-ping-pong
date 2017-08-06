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
    return Message.createWarning(e.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public Message handleException(Throwable e) {
    return Message.createDanger(e.getMessage()).build();
  }
}
