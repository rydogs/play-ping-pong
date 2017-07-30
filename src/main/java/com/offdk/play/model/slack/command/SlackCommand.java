package com.offdk.play.model.slack.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.User;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSlackCommand.class)
@JsonDeserialize(as = ImmutableSlackCommand.class)
public interface SlackCommand {

  String token();

  String teamId();

  String teamDomain();

  String channelId();

  String channelName();

  String command();

  User commandUser();

  String text();

  String responseUrl();
}
