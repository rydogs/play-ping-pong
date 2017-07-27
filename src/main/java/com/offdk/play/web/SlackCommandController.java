package com.offdk.play.web;

import com.offdk.play.model.slack.SlackCommand;
import com.offdk.play.service.SlackChatService;
import com.offdk.play.service.SlackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SlackCommandController {

  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandController.class);

  private final SlackChatService chatService;

  public SlackCommandController(SlackChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/command")
  @ResponseBody
  public SlackResponse command(@SlackCommandParam SlackCommand command) {
    LOGGER.info("Request: {}" + command);
    return chatService.respond(command);
  }
}
