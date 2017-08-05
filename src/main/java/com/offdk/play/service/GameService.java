package com.offdk.play.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.Action;
import com.offdk.play.model.slack.request.Attachment;
import com.offdk.play.model.slack.request.ImmutableMessage;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.request.Style;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.model.slack.response.SlackCommand;
import com.offdk.play.persistence.MatchRepository;
import com.offdk.play.persistence.PlayerRepository;

@Service
public class GameService {
  private static final String CHALLENGE_FMT = "%s challenged %s for a game.";

  private final MatchRepository matchRepo;
  private final PlayerRepository playerRepo;

  public GameService(MatchRepository matchRepo, PlayerRepository playerRepo) {
    this.matchRepo = matchRepo;
    this.playerRepo = playerRepo;
  }

  public Message challenge(SlackCommand command) {
    Preconditions.checkState(command.mentionedUsers().size() > 0, "Must mention another user to challenge");
    String teamId = command.team().getId();
    Player challenger = findOrCreate(teamId, command.commandUser());
    Player challenged = findOrCreate(teamId, command.mentionedUsers().get(0));
    Match match = createMatch(teamId, challenger, challenged);
    String challengeText = String.format(CHALLENGE_FMT, challenger.getUser(), challenged.getUser());
    Message msg = Message.createInChannelMessage()
        .text("Challenger " + challenger + ", Challenged: " + challenged)
        .addAttachments(
            Attachment.createAttachment(match.getId(), challengeText)
            .callbackId(match.getId())
            .addActions(
                Action.createButton("accept", "Accept", Style.PRIMARY),
                Action.createButton("refuse", "Refuse", Style.DANGER))
            .build())
        .build();
    return msg;
  }

  public Message accept(CallbackRequest request) {
    Match match = matchRepo.findOne(request.callbackId());
    match.accept();
    matchRepo.save(match);
    return ImmutableMessage.builder().text("Responding to request: " + match)
        .build();
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
