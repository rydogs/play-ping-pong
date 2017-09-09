package com.offdk.play.model.slack.request.parser;

import java.util.function.Function;

public class SanitizedTextParser implements Function<String, String> {

  @Override
  public String apply(String text) {
    return text.replaceAll("\\<.*\\>", "");
  }

}
