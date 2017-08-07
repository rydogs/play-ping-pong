package com.offdk.play.service;

import java.util.function.Predicate;

import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.MatchStatus;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;

public interface CallbackProcessor {
  CallbackName name();

  Message process(CallbackRequest request);

  default Predicate<Match> isChallegedUser(User user) {
    return m -> m.getChallenged().stream().map(c -> c.getUser())
        .anyMatch(u -> u.equals(user));
  }

  default Predicate<Match> challengedStatus() {
    return m -> MatchStatus.CHALLENGED.equals(m.getStatus());
  }
}
