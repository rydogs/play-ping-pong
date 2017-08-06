package com.offdk.play.service;

import org.springframework.stereotype.Component;

import com.offdk.play.exception.MatchException;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.persistence.MatchRepository;

import io.vavr.control.Try;

@Component
public class RefuseMatchCallbackPrecessor implements CallbackProcessor {
  private final MatchRepository matchRepo;

  public RefuseMatchCallbackPrecessor(MatchRepository matchRepo) {
    this.matchRepo = matchRepo;
  }

  @Override
  public CallbackName name() {
    return CallbackName.REFUSE_MATCH;
  }

  @Override
  public Message process(CallbackRequest request) {
    return Try.of(() -> matchRepo.findOne(request.callbackId()))
        .filter(isChallegedUser(request.user()))
        .onFailure(MatchException.ofThrow("Only the challenged can refuse the game"))
        .filter(challengedStatus())
        .map(m -> {
          m.refuse();
          matchRepo.save(m);
          return Message.createSuccess(m.refuseMatchText()).build();
        }).getOrElseThrow(() -> MatchException.of("Match has already started"));
  }
}
