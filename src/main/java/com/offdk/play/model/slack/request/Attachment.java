package com.offdk.play.model.slack.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutableAttachment.class)
@JsonDeserialize(as = ImmutableAttachment.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public interface Attachment {

  @Nullable
  String title();

  String fallback();

  String callbackId();

  @Nullable
  String color();

  List<Action> actions();

  @Nullable
  AttachmentType attachmentType();

  static ImmutableAttachment.Builder createAttachment(String refId, String title) {
    return createAttachment(refId, title, title);
  }

  static ImmutableAttachment.Builder createAttachment(String refId, String title, String defaultMessage) {
    return ImmutableAttachment.builder()
        .title(title)
        .fallback(defaultMessage)
        .callbackId(refId)
        .actions(Lists.newArrayList())
        .attachmentType(AttachmentType.DEFAULT);
  }

  @Value.Check
  default void check() {
    Preconditions.checkState(this.actions().size() <= 5,
        "A maximum of 5 actions per attachment may be provided");
  }
}
