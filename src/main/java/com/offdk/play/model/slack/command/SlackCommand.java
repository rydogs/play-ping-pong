package com.offdk.play.model.slack.command;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.Channel;
import com.offdk.play.model.slack.Team;
import com.offdk.play.model.slack.User;

@Value.Immutable
@JsonSerialize(as = ImmutableSlackCommand.class)
@JsonDeserialize(as = ImmutableSlackCommand.class)
public interface SlackCommand {

  String token();

  Team team();

  Channel channel();

  String command();

  User commandUser();

  String text();

  String responseUrl();
}
