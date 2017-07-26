package com.offdk.play.service;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

/**
 * Slack response is json with snake case, for example:
 * https://api.slack.com/slash-commands
 *   {
        "response_type": "in_channel",
        "text": "It's 80 degrees right now.",
        "attachments": [
            {
                "text":"Partly cloudy today and tomorrow"
            }
        ]
      }
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SlackResponse {
  private final ResponseType responseType;
  private final String text;
  private final List<Attachment> attachments;

  @VisibleForTesting
  SlackResponse(ResponseType responseType, String text, List<Attachment> attachments) {
    this.responseType = responseType;
    this.text = text;
    this.attachments = attachments;
  }

  public ResponseType getResponseType() {
    return responseType;
  }

  public String getText() {
    return text;
  }

  public List<Attachment> getAttachments() {
    return attachments;
  }

  @VisibleForTesting
  enum ResponseType {in_channel, ephemeral}

  // in channel response is for everyone to see
  public static SlackResponse inChannel(String text) {
    return new SlackResponse(ResponseType.in_channel, text, Lists.<Attachment>newArrayList());
  }

  // ephemeral response is only visible to the sender
  public static SlackResponse ephemeral(String text) {
    return new SlackResponse(ResponseType.ephemeral, text, Lists.<Attachment>newArrayList());
  }
}
