package com.offdk.play.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.Action;
import com.offdk.play.model.slack.request.Attachment;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.request.Style;
import com.offdk.play.model.slack.response.SlackCommand;
import com.offdk.play.persistence.PlayerRepository;

@Service
public class SlackChatService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SlackChatService.class);
  private static final String CHALLENGE_FMT = "%s challenged %s for a game.";

  private final PlayerRepository playerRepo;

  public SlackChatService(PlayerRepository playerRepo) {
    this.playerRepo = playerRepo;
  }

  public Message challenge(SlackCommand command) {
    Preconditions.checkState(command.mentionedUsers().size() > 0, "Must mention another user to challenge");
    Player challenger = findOrCreate(command.team().getId(), command.commandUser());
    Player challenged = findOrCreate(command.team().getId(), command.mentionedUsers().get(0));
    String challengeText = String.format(CHALLENGE_FMT, challenger.getUser(), challenged.getUser());
    Message msg = Message.createInChannelMessage()
        .text("Challenger " + challenger + ", Challenged: " + challenged)
        .addAttachments(Attachment.createAttachment("accept_challenge", challengeText)
            .addActions(
                Action.createButton("accept", "Accept", Style.PRIMARY),
                Action.createButton("refuse", "Refuse", Style.DANGER)))
        .build();

    LOGGER.info(msg.toString());

    return msg;
  }

  @VisibleForTesting
  Player findOrCreate(String teamId, User user) {
    return Optional.ofNullable(user)
        .flatMap(u -> playerRepo.findOneByUserId(u.getId()))
        .orElseGet(
            () -> playerRepo.save(Player.newPlayer(teamId, user)));
  }
}
