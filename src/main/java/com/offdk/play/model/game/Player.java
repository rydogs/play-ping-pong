package com.offdk.play.model.game;

import com.google.common.collect.Maps;
import com.offdk.play.model.slack.User;
import com.offdk.play.rating.RatingKey;
import com.offdk.play.rating.RatingType;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class Player {

  private final User user;

  private Map<RatingType, Map<RatingKey, BigDecimal>> currentRating;

  public Player(User user) {
    this.user = user;
    this.currentRating = Maps.newHashMap();
  }

  public void updateCurrentRatings(Map<RatingType, Map<RatingKey, BigDecimal>> newRatings) {
    currentRating = newRatings;
  }

  public Optional<Map<RatingKey, BigDecimal>> getCurrentRating(RatingType ratingType) {
    return Optional.ofNullable(currentRating.get(ratingType));
  }
}
