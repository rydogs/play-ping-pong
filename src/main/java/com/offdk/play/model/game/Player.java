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
    this.id = user.getId();
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

  public String getId() {
    return id;
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

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this.getClass()).add("id", id).add("user", user)
        .add("teamId", slackTeamId).toString();
  }
}
