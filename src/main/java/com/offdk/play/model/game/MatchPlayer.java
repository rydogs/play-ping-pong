package com.offdk.play.model.game;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.annotation.PersistenceConstructor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.offdk.play.model.slack.User;
import com.offdk.play.rating.RatingType;

import io.vavr.control.Try;

public class MatchPlayer {
  private final User user;
  private final MatchPlayerType type;
  private Optional<Integer> score;
  private Optional<BigDecimal> rating;

  // Needed to allow spring-data to serialize data to object
  @PersistenceConstructor
  MatchPlayer(User user, MatchPlayerType type, Optional<Integer> score, Optional<BigDecimal> rating) {
    this.user = user;
    this.type = type;
    this.score = score;
    this.rating = rating;
  }

  @VisibleForTesting
  MatchPlayer(Player player, MatchPlayerType type, Integer score) {
    this.user = player.getUser();
    // TODO: remove hard coding to rating type or remove multiple rating on player
    this.rating = Try.of(() -> player.getCurrentRating().get(RatingType.ELO).getRating()).toJavaOptional();
    this.type = type;
    this.score = Optional.ofNullable(score);
  }

  public Optional<Integer> getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = Optional.of(score);
  }

  public MatchPlayerType getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  public Optional<BigDecimal> getRating() {
    return rating;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(MatchPlayer.class).add("user", user).add("type", type)
        .add("score", score).toString();
  }

  public static MatchPlayer newChallenger(Player player) {
    return new MatchPlayer(player, MatchPlayerType.CHALLENGER, null);
  }

  public static MatchPlayer newChallenged(Player player) {
    return new MatchPlayer(player, MatchPlayerType.CHALLENGED, null);
  }
}
