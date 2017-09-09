package com.offdk.play.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offdk.play.model.slack.message.Message;
import com.offdk.play.model.slack.request.SlackCommand;
import com.offdk.play.service.GameService;

@Controller
public class SlackCommandController {

  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandController.class);

  private final GameService gameService;

  public SlackCommandController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping("/command")
  @ResponseBody
  public Message command(@SlackCommandParam SlackCommand command) {
    LOGGER.info("Request: {}", command);
    return gameService.handleCommand(command);
  }
}
