package com.offdk.play.model;

public class PlaySlackCommand extends SlackCommand {
  public SubCommand getSubComamnd() {
    //TODO: parse command text to determine sub commands: challenge, accept, socre, top...
    return SubCommand.Accept;
  }
}