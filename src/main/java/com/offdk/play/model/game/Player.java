package com.offdk.play.model.game;

import com.google.common.collect.Maps;
import com.offdk.play.model.slack.User;
import com.offdk.play.rating.Rating;
import com.offdk.play.rating.RatingType;
import java.util.Map;
import java.util.Optional;

public class Player {

  private final User user;

  private Map<RatingType, Rating> currentRating;

  public Player(User user) {
    this.user = user;
    this.currentRating = Maps.newHashMap();
  }

  public void updateCurrentRatings(RatingType ratingType, Rating rating) {
    currentRating.put(ratingType, rating);
  }

  public Optional<Rating> getCurrentRating(RatingType ratingType) {
    return Optional.ofNullable(currentRating.get(ratingType));
  }
}
