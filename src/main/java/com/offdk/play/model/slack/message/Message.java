package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutableMessage.class)
@JsonDeserialize(as = ImmutableMessage.class)
@Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public abstract class Message {

  @Nullable
  public abstract String text();

  @Nullable
  public abstract List<Attachment> attachments();

  @Nullable
  public abstract String threadTimestamp();

  @Nullable
  public abstract String responseType();

  @Nullable
  public abstract Boolean replaceOriginal();

  @Nullable
  public abstract Boolean deleteOriginal();

  public static Message createInChannelMessage() {
    return ImmutableMessage.builder()
        .responseType(ResponseType.IN_CHANNEL.toString())
        .build();
  }

  public static Message createEphemeralMessage() {
    return ImmutableMessage.builder()
        .responseType(ResponseType.EPHEMERAL.toString())
        .build();
  }

  public Message addText(String text) {
    return ImmutableMessage.builder()
        .text(text)
        .attachments(this.attachments())
        .threadTimestamp(this.threadTimestamp())
        .responseType(this.responseType())
        .replaceOriginal(this.replaceOriginal())
        .deleteOriginal(this.deleteOriginal())
        .build();
  }

  public Message addAttachment(Attachment attachment) {
    return ImmutableMessage.builder()
        .text(this.text())
        .addAttachments(attachment)
        .threadTimestamp(this.threadTimestamp())
        .responseType(this.responseType())
        .replaceOriginal(this.replaceOriginal())
        .deleteOriginal(this.deleteOriginal())
        .build();
  }

  public Message addAttachments(Attachment... attachments) {
    return ImmutableMessage.builder()
        .text(this.text())
        .addAttachments(attachments)
        .threadTimestamp(this.threadTimestamp())
        .responseType(this.responseType())
        .replaceOriginal(this.replaceOriginal())
        .deleteOriginal(this.deleteOriginal())
        .build();
  }
}
