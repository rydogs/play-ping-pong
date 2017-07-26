package com.offdk.play.rating;

import com.google.common.collect.Lists;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EloRatingCalculatorTest {

  private Player playerOne;
  private Player playerTwo;
  private Player playerThree;

  private RatingCalculator calculator;

  @Before
  public void setUp() throws Exception {
    playerOne = new Player(new User("0", "Rick Sanchez"));
    playerTwo = new Player(new User("1", "Morty Smith"));
    playerThree = new Player(new User("2", "Mr. Meeseeks"));

    calculator = new EloRatingCalculator();
  }

  @Test
  public void calculate() throws Exception {
    // Match One
    Match matchOne = new Match(Lists.newArrayList(playerOne, playerTwo));
    matchOne.addScore(playerOne, 21);
    matchOne.addScore(playerTwo, 18);

    List<Rating> result = calculator.calculate(matchOne);
    for (int idx = 0; idx < matchOne.getPlayers().size(); idx++) {
      matchOne.getPlayers().get(idx)
          .updateCurrentRatings(calculator.getRatingType(), result.get(idx));
    }

    Assert.assertEquals(BigDecimal.valueOf(1510.0),
        playerOne.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));
    Assert.assertEquals(BigDecimal.valueOf(1490.0),
        playerTwo.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));

    // Match Two
    Match matchTwo = new Match(Lists.newArrayList(playerOne, playerTwo));
    matchTwo.addScore(playerOne, 21);
    matchTwo.addScore(playerTwo, 17);

    result = calculator.calculate(matchTwo);
    for (int idx = 0; idx < matchOne.getPlayers().size(); idx++) {
      matchTwo.getPlayers().get(idx)
          .updateCurrentRatings(calculator.getRatingType(), result.get(idx));
    }

    Assert.assertEquals(BigDecimal.valueOf(1519.4),
        playerOne.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));
    Assert.assertEquals(BigDecimal.valueOf(1480.6),
        playerTwo.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));

    // Match Three
    Match matchThree = new Match(Lists.newArrayList(playerOne, playerTwo));
    matchThree.addScore(playerOne, 14);
    matchThree.addScore(playerTwo, 21);

    result = calculator.calculate(matchThree);
    for (int idx = 0; idx < matchOne.getPlayers().size(); idx++) {
      matchThree.getPlayers().get(idx)
          .updateCurrentRatings(calculator.getRatingType(), result.get(idx));
    }

    Assert.assertEquals(BigDecimal.valueOf(1508.3),
        playerOne.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));
    Assert.assertEquals(BigDecimal.valueOf(1491.7),
        playerTwo.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
            .setScale(1, RoundingMode.HALF_UP));
  }

  @Test(expected = NullPointerException.class)
  public void calculateMissingMatch() throws Exception {
    calculator.calculate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateNotEnoughPlayers() throws Exception {
    calculator.calculate(new Match(Lists.newArrayList(playerOne)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateTooManyPlayers() throws Exception {
    calculator.calculate(new Match(Lists.newArrayList(playerOne, playerTwo, playerThree)));
  }
}