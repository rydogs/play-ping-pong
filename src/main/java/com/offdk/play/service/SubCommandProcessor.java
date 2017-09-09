package com.offdk.play.service;

import com.offdk.play.model.slack.message.Message;
import com.offdk.play.model.slack.request.SlackCommand;
import com.offdk.play.model.slack.request.SubCommand;

public interface SubCommandProcessor {
  SubCommand subcommand();

  Message process(SlackCommand command);
}
