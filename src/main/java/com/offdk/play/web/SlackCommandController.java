package com.offdk.play.web;

import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.SlackCommand;
import com.offdk.play.service.SlackChatService;
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
  public Message command(@SlackCommandParam SlackCommand command) {
    LOGGER.info("Request: {}" + command);
    return chatService.respond(command);
  }
}
