package com.offdk.play.model.game;


import com.offdk.play.model.slack.User;
import com.offdk.play.rating.EloRating;
import com.offdk.play.rating.Rating;
import com.offdk.play.rating.RatingType;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

  private User user;

  @Before
  public void setUp() throws Exception {
    this.user = new User("0", "Rick Sanchez");
  }

  @Test
  public void hasCurrentRatings() throws Exception {
    Player player = new Player(user);
    player.updateCurrentRatings(RatingType.ELO, new EloRating(BigDecimal.valueOf(1234.56)));

    Optional<BigDecimal> rating = player.getCurrentRating(RatingType.ELO).map(Rating::getRating);

    Assert.assertTrue(rating.isPresent());
    Assert.assertEquals(1234.56, rating.get().doubleValue(), 0);
  }

  @Test
  public void missingCurrentRatings() throws Exception {
    Player player = new Player(user);

    Optional<BigDecimal> rating = player.getCurrentRating(RatingType.ELO).map(Rating::getRating);

    Assert.assertFalse(rating.isPresent());
  }
}