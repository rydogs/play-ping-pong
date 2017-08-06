package com.offdk.play.service;

import org.springframework.stereotype.Component;

import com.offdk.play.exception.MatchException;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.persistence.MatchRepository;

import io.vavr.control.Try;

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
    return Try.of(() -> matchRepo.findOne(request.callbackId()))
        .filter(isChallegedUser(request.user()))
        .onFailure(MatchException.ofThrow("Only the challenged can accept the game"))
        .filter(challengedStatus())
        .map(m -> {
          m.accept();
          matchRepo.save(m);
          return Message.createSuccess(m.acceptMatchText()).build();
        }).getOrElseThrow(() -> MatchException.of("Match can not be started"));
  }
}
