package com.offdk.play.model.slack.message;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.offdk.play.model.slack.message.ImmutableOptionGroup;

@Value.Immutable
@JsonSerialize(as = ImmutableOptionGroup.class)
@JsonDeserialize(as = ImmutableOptionGroup.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
interface OptionGroup {

  String text();

  List<Option> options();
}
