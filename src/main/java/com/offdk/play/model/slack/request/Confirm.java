package com.offdk.play.model.slack.request;

import javax.annotation.Nullable;

import org.immutables.value.Value;
import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonSerialize(as = ImmutableConfirm.class)
@JsonDeserialize(as = ImmutableConfirm.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public interface Confirm {

  @Nullable
  String title();

  String text();

  @Nullable
  String okText();

  @Nullable
  String dismissText();
}
