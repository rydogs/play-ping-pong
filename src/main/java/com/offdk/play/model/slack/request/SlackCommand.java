package com.offdk.play.model.slack.request;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.Channel;
import com.offdk.play.model.slack.Team;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.parser.SanitizedTextParser;
import com.offdk.play.model.slack.request.parser.SubCommandParser;
import com.offdk.play.model.slack.request.parser.UserParser;
import com.offdk.play.model.slack.request.ImmutableSlackCommand;

@Value.Immutable
@JsonSerialize(as = ImmutableSlackCommand.class)
@JsonDeserialize(as = ImmutableSlackCommand.class)
public interface SlackCommand {
  final static UserParser userParser = new UserParser();
  final static SubCommandParser subCommandParser = new SubCommandParser();
  final static SanitizedTextParser santizedTextParser = new SanitizedTextParser();

  String token();

  Team team();

  Channel channel();

  String command();

  User commandUser();

  String text();

  String responseUrl();

  default List<User> mentionedUsers() {
    return userParser.apply(text());
  }

  default Optional<SubCommand> subCommand() {
    return subCommandParser.apply(text());
  }

  default String santizedText() {
    return santizedTextParser.apply(text());
  }
}
