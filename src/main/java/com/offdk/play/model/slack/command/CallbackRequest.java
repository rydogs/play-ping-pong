package com.offdk.play.model.slack.command;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.Channel;
import com.offdk.play.model.slack.Team;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.message.Action;

@Value.Immutable
@JsonSerialize(as = ImmutableCallbackRequest.class)
@JsonDeserialize(as = ImmutableCallbackRequest.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public interface CallbackRequest {
  String callbackId();

  Team team();

  Channel channel();

  User user();

  String actionTs();

  String messageTs();

  String responseUrl();

  List<Action> actions();
}
