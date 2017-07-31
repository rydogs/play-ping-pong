package com.offdk.play.model.slack.command;

public abstract class PlaySlackCommand implements SlackCommand {

  public SubCommand getSubComamnd() {
    //TODO: parse command text to determine sub commands: challenge, accept, score, top...
    return SubCommand.ACCEPT;
  }
}
