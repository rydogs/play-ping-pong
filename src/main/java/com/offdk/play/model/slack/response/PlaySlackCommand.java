package com.offdk.play.model.slack.response;

public abstract class PlaySlackCommand implements SlackCommand {

  public SubCommand getSubComamnd() {
    //TODO: parse response text to determine sub commands: challenge, accept, score, top...
    return SubCommand.ACCEPT;
  }
}
