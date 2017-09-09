package com.offdk.play.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.offdk.play.exception.MatchException;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.slack.message.Action;
import com.offdk.play.model.slack.message.Attachment;
import com.offdk.play.model.slack.message.Message;
import com.offdk.play.model.slack.message.Style;
import com.offdk.play.model.slack.request.SlackCommand;
import com.offdk.play.model.slack.request.SubCommand;
import com.offdk.play.persistence.MatchRepository;
import com.offdk.play.persistence.PlayerRepository;

@Component
public class ScoreSubCommandProcessor implements SubCommandProcessor {
  static final String SCORE_FMT = "%s reported score for the match: %s";
  private final MatchRepository matchRepo;
  private final PlayerRepository playerRepo;

  ScoreSubCommandProcessor(MatchRepository matchRepo, PlayerRepository playerRepo) {
    this.matchRepo = matchRepo;
    this.playerRepo = playerRepo;
  }

  @Override
  public SubCommand subcommand() {
    return SubCommand.SCORE;
  }

  @Override
  public Message process(SlackCommand command) {
    String teamId = command.team().getId();
    Match match = matchRepo.findActiveMatch(teamId, command.commandUser().getId())
        .orElseThrow(() ->
        MatchException.of("You are not currently part of an active match."
            + "Please challenge someone first before reporting score"));
    List<Integer> scores = parseScore(command);
    Preconditions.checkState(scores.size() == match.getPlayers().size(),
        "Score size must match player size");
    for (int i = 0; i < match.getPlayers().size(); i++) {
      match.getPlayers().get(i).setScore(scores.get(i));
    }
    return Message.createInChannelMessage()
        .addAttachments(
            Attachment.createAttachment(match.getId(),
                String.format(SCORE_FMT, command.commandUser(), scoreText(match)))
            .callbackId(match.getId())
            .addActions(
                Action.createButton(CallbackName.ACCEPT_SCORE.toString(), "Accept", Style.PRIMARY),
                Action.createButton(CallbackName.REJECT_SCORE.toString(), "Refuse", Style.DANGER))
            .build())
        .build();
  }

  List<Integer> parseScore(SlackCommand command) {
    return Arrays.asList(command.santizedText().split(":")).stream().map(s -> Integer.parseInt(s))
        .collect(Collectors.toList());
  }

  String scoreText(Match match) {
    return match.getPlayers().stream().map(p -> p.getUser() + " : " + p.getScore())
        .collect(Collectors.joining(", "));
  }
}
