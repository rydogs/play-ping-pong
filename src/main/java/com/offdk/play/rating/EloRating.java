package com.offdk.play.rating;

import java.math.BigDecimal;

public class EloRating implements Rating {

  private final BigDecimal rating;

  public EloRating(BigDecimal rating) {
    this.rating = rating;
  }

  @Override
  public BigDecimal getRating() {
    return rating;
  }
}
