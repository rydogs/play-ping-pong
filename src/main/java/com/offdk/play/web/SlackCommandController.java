package com.offdk.play.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class SlackCommandController {
  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandController.class);
  @PostMapping("/command")
  @ResponseStatus(HttpStatus.OK)
  public void command(@RequestBody String json) {
    LOGGER.info("Request: {}" + json);
  }
}
