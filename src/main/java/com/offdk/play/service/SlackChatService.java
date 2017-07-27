package com.offdk.play.service;

import com.offdk.play.model.slack.SlackCommand;
import org.springframework.stereotype.Service;

@Service
public class SlackChatService {

  public SlackResponse respond(SlackCommand command) {
    //TODO implement actual logic to respond to command. For now, echo
    return SlackResponse.inChannel("Echo: " + command.getText());
  }
}
