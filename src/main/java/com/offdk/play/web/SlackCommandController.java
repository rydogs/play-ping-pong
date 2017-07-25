package com.offdk.play.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.offdk.play.model.slack.SlackCommand;

@Controller
public class SlackCommandController {
  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandController.class);
  @PostMapping("/command")
  @ResponseStatus(HttpStatus.OK)
  public void command(@SlackCommandParam SlackCommand command) {
    LOGGER.info("Request: {}" + command);
  }
}
