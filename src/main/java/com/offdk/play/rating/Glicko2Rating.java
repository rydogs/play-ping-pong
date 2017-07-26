package com.offdk.play.rating;

import java.math.BigDecimal;

public class Glicko2Rating implements Rating {

  private final BigDecimal rating;
  private final BigDecimal ratingDeviation;

  public Glicko2Rating(BigDecimal rating, BigDecimal ratingDeviation) {
    this.rating = rating;
    this.ratingDeviation = ratingDeviation;
  }

  @Override
  public BigDecimal getRating() {
    return rating;
  }

  public BigDecimal getRatingDeviation() {
    return ratingDeviation;
  }
}
