package com.offdk.play.model.slack.response;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.Channel;
import com.offdk.play.model.slack.Team;
import com.offdk.play.model.slack.User;
import com.offdk.play.web.UserParser;

@Value.Immutable
@JsonSerialize(as = ImmutableSlackCommand.class)
@JsonDeserialize(as = ImmutableSlackCommand.class)
public interface SlackCommand {
  static final UserParser userParser = new UserParser();

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
}
