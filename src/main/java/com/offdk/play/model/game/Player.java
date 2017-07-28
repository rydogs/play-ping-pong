package com.offdk.play.model.game;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.offdk.play.model.slack.User;
import com.offdk.play.rating.Rating;
import com.offdk.play.rating.RatingType;

@Document
public class Player {
  @Id
  private String id;
  private String slackTeamId;
  private User user;

  private Map<RatingType, Rating> currentRating;

  @VisibleForTesting
  Player(String slackTeamId, User user) {
    this.slackTeamId = slackTeamId;
    this.user = user;
    this.currentRating = Maps.newHashMap();
  }

  public Map<RatingType, Rating> getCurrentRating() {
    return currentRating;
  }

  public void setCurrentRating(Map<RatingType, Rating> currentRating) {
    this.currentRating = currentRating;
  }

  public String getSlackTeamId() {
    return slackTeamId;
  }

  public User getUser() {
    return user;
  }

  public void updateCurrentRatings(RatingType ratingType, Rating rating) {
    currentRating.put(ratingType, rating);
  }

  public Optional<Rating> getCurrentRating(RatingType ratingType) {
    return Optional.ofNullable(currentRating.get(ratingType));
  }

  public static Player newPlayer(String slackTeamId, User user) {
    return new Player(slackTeamId, user);
  }

  public String toString() {
    return MoreObjects.toStringHelper(this.getClass()).add("id", id).add("user", user)
        .add("teamId", slackTeamId).toString();
  }
}
