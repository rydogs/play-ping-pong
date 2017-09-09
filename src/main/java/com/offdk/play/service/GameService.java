package com.offdk.play.service;

import com.google.common.annotations.VisibleForTesting;
import com.offdk.play.model.slack.message.Message;
import com.offdk.play.model.slack.request.CallbackRequest;
import com.offdk.play.model.slack.request.SlackCommand;
import com.offdk.play.model.slack.request.SubCommand;
import com.offdk.play.persistence.MatchRepository;
import com.offdk.play.persistence.PlayerRepository;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameService {
  private final MatchRepository matchRepo;
  private final PlayerRepository playerRepo;
  private Map<CallbackName, CallbackProcessor> callbackProcessors;
  private Map<SubCommand, SubCommandProcessor> subCommandProcessors;

  public GameService(MatchRepository matchRepo, PlayerRepository playerRepo,
      Set<CallbackProcessor> callbackProcessors, Set<SubCommandProcessor> subCommandProcessors) {
    this.matchRepo = matchRepo;
    this.playerRepo = playerRepo;
    this.callbackProcessors = callbackProcessors.stream().collect(
        Collectors.toMap(p -> p.callback(), Function.identity()));
    this.subCommandProcessors = subCommandProcessors.stream().collect(
        Collectors.toMap(p -> p.subcommand(), Function.identity()));
  }

  public Message handleCommand(SlackCommand command) {
    return command.subCommand().map(c -> subCommandProcessors.get(c)).map(p -> p.process(command))
        .orElseGet(() -> unknownCommand());
  }


  public Message handleCallback(CallbackRequest request) {
    //TODO: handle more than one action by combining messages
    CallbackName name = CallbackName.valueOf(request.actions().get(0).name());
    return callbackProcessors.get(name).process(request);
  }

  @VisibleForTesting
  static Message unknownCommand() {
    return Message.createEphemeralMessage().text("Unknow command, please use /play challenge to challenge"
        + "another play or or /play score to report score").build();
  }
}
