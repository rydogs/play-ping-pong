package com.offdk.play.model.slack.request.parser;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Function;

import com.offdk.play.model.slack.request.SubCommand;

public class SubCommandParser implements Function<String, Optional<SubCommand>> {

  @Override
  public Optional<SubCommand> apply(String t) {
    return EnumSet.allOf(SubCommand.class).stream()
        .filter(sc -> t.toLowerCase().contains(sc.name().toLowerCase())).findFirst();
  }
}
