package com.offdk.play.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.Action;
import com.offdk.play.model.slack.request.Attachment;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.request.Style;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.model.slack.response.SlackCommand;
import com.offdk.play.persistence.MatchRepository;
import com.offdk.play.persistence.PlayerRepository;

@Service
public class GameService {
  private final MatchRepository matchRepo;
  private final PlayerRepository playerRepo;
  private Map<CallbackName, CallbackProcessor> callbackProcessors;

  public GameService(MatchRepository matchRepo, PlayerRepository playerRepo,
      List<CallbackProcessor> processors) {
    this.matchRepo = matchRepo;
    this.playerRepo = playerRepo;
    this.callbackProcessors = processors.stream().collect(
        Collectors.toMap(p -> p.name(), Function.identity()));
  }

  public Message challenge(SlackCommand command) {
    Preconditions.checkState(command.mentionedUsers().size() > 0, "Must mention another user to challenge");
    Preconditions.checkState(
        !command.mentionedUsers().stream().anyMatch(u -> u.equals(command.commandUser())),
        "Silly, you should challenge yourself elsewhere...");
    String teamId = command.team().getId();
    Player challenger = findOrCreate(teamId, command.commandUser());
    Player challenged = findOrCreate(teamId, command.mentionedUsers().get(0));
    Match match = createMatch(teamId, challenger, challenged);
    Message msg = Message.createInChannelMessage()
        .addAttachments(
            Attachment.createAttachment(match.getId(), match.challengeText())
            .callbackId(match.getId())
            .addActions(
                Action.createButton(CallbackName.ACCEPT_MATCH.toString(), "Accept", Style.PRIMARY),
                Action.createButton(CallbackName.REFUSE_MATCH.toString(), "Refuse", Style.DANGER))
            .build())
        .build();
    return msg;
  }

  public Message handleCallback(CallbackRequest request) {
    //TODO: handle more than one action by combining messages
    CallbackName name = CallbackName.valueOf(request.actions().get(0).name());
    return callbackProcessors.get(name).process(request);
  }

  @VisibleForTesting
  Player findOrCreate(String teamId, User user) {
    return Optional.ofNullable(user)
        .map(u -> playerRepo.findOne(u.getId()))
        .orElseGet(
            () -> playerRepo.save(Player.newPlayer(teamId, user)));
  }

  @VisibleForTesting
  Match createMatch(String teamId, Player... players) {
    return matchRepo.save(new Match(teamId, Lists.newArrayList(players)));
  }
}
