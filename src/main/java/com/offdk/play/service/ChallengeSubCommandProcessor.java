package com.offdk.play.service;

import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.message.Action;
import com.offdk.play.model.slack.message.Attachment;
import com.offdk.play.model.slack.message.Message;
import com.offdk.play.model.slack.message.Style;
import com.offdk.play.model.slack.request.SlackCommand;
import com.offdk.play.model.slack.request.SubCommand;
import com.offdk.play.persistence.MatchRepository;
import com.offdk.play.persistence.PlayerRepository;

@Component
public class ChallengeSubCommandProcessor implements SubCommandProcessor {
  private final MatchRepository matchRepo;
  private final PlayerRepository playerRepo;

  ChallengeSubCommandProcessor(MatchRepository matchRepo, PlayerRepository playerRepo) {
    this.matchRepo = matchRepo;
    this.playerRepo = playerRepo;
  }

  @Override
  public SubCommand subcommand() {
    return SubCommand.CHALLENGE;
  }

  @Override
  public Message process(SlackCommand command) {
    Preconditions.checkState(command.mentionedUsers().size() > 0, "Must mention another user to challenge");
    Preconditions.checkState(
        !command.mentionedUsers().stream().anyMatch(u -> u.equals(command.commandUser())),
        "Silly, you should challenge yourself elsewhere...");
    String teamId = command.team().getId();
    Player challenger = playerRepo.findOrCreate(teamId, command.commandUser());
    Player challenged = playerRepo.findOrCreate(teamId, command.mentionedUsers().get(0));
    Match match = createMatch(teamId, challenger, challenged);

    return Message.createInChannelMessage()
        .addAttachments(
            Attachment.createAttachment(match.getId(), match.challengeText())
            .callbackId(match.getId())
            .addActions(
                Action.createButton(CallbackName.ACCEPT_MATCH.toString(), "Accept", Style.PRIMARY),
                Action.createButton(CallbackName.REFUSE_MATCH.toString(), "Refuse", Style.DANGER))
            .build())
        .build();
  }

  @VisibleForTesting
  Match createMatch(String teamId, Player... players) {
    return matchRepo.save(new Match(teamId, Lists.newArrayList(players)));
  }

}
