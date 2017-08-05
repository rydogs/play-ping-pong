package com.offdk.play.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.offdk.play.exception.MatchException;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.MatchStatus;
import com.offdk.play.model.slack.request.ImmutableMessage;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.persistence.MatchRepository;

@Component
public class AcceptMatchCallbackProcessor implements CallbackProcessor {
  private final MatchRepository matchRepo;

  public AcceptMatchCallbackProcessor(MatchRepository matchRepo) {
    this.matchRepo = matchRepo;
  }

  @Override
  public CallbackName name() {
    return CallbackName.ACCEPT_MATCH;
  }

  @Override
  public Message process(CallbackRequest request) {
    Match match = matchRepo.findOne(request.callbackId());
    return Optional.ofNullable(match).filter(m -> MatchStatus.CHALLENGED.equals(m.getStatus()))
        .map(m -> {
          match.accept();
          matchRepo.save(match);
          return ImmutableMessage.builder().text("Responding to request: " + match).build();
        }).orElseThrow(() -> MatchException.of("Match has already started"));
  }
}
