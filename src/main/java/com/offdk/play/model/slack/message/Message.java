package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.offdk.play.model.slack.message.ImmutableMessage;
import com.offdk.play.model.slack.message.ImmutableMessage.Builder;

import java.util.List;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutableMessage.class)
@JsonDeserialize(as = ImmutableMessage.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public interface Message {

  @Nullable
  String text();

  @Nullable
  List<Attachment> attachments();

  @Nullable
  String threadTimestamp();

  @Nullable
  ResponseType responseType();

  @Default
  default Boolean replaceOriginal() {
    return false;
  };

  @Default
  default Boolean deleteOriginal() {
    return false;
  };


  @Value.Check
  default void check() {
    Preconditions.checkState(attachments() == null || attachments().size() <= 20,
        "Messages should contain no more than 20 attachments");
  }

  static Builder createInChannelMessage() {
    return ImmutableMessage.builder()
        .responseType(ResponseType.IN_CHANNEL);
  }

  static Builder createEphemeralMessage() {
    return ImmutableMessage.builder()
        .responseType(ResponseType.EPHEMERAL);
  }

  static Builder createWarning(String message) {
    return createEphemeralMessage().addAttachments(
        Attachment.warningText(message).build());
  }

  static Builder createDanger(String message) {
    return createEphemeralMessage().addAttachments(
        Attachment.dangerText(message).build());
  }

  static Builder createSuccess(String message) {
    return createInChannelMessage().replaceOriginal(true).addAttachments(
        Attachment.successText(message).build());
  }
}
