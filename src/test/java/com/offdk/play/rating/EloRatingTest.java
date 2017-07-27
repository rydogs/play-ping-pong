package com.offdk.play.rating;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class EloRatingTest {

  @Test
  public void getRating() throws Exception {
    EloRating rating = new EloRating(BigDecimal.valueOf(1234.56));

    Assert.assertEquals(1234.56, rating.getRating().doubleValue(), 0);
  }
}