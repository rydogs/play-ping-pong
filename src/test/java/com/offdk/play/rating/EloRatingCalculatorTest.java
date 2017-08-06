package com.offdk.play.rating;

import com.google.common.collect.Lists;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;

import io.vavr.collection.Stream;

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
    playerOne = Player.newPlayer("2", new User("0", "Rick Sanchez"));
    playerTwo = Player.newPlayer("4", new User("1", "Morty Smith"));
    playerThree = Player.newPlayer("3", new User("2", "Mr. Meeseeks"));

    calculator = new EloRatingCalculator();
  }

  @Test
  public void calculate() throws Exception {
    // Match One
    List<Player> matchOnePlayers = Lists.newArrayList(playerOne, playerTwo);
    Match matchOne = new Match("", matchOnePlayers);
    matchOne.reportScores(21, 18);

    List<Rating> result = calculator.calculate(matchOne);
    Stream.concat(result).zipWithIndex()
    .forEach(t -> matchOnePlayers.get(t._2).updateCurrentRatings(calculator.getRatingType(), t._1));

    Assert.assertEquals(BigDecimal.valueOf(1510.0),
        playerOne.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
        .setScale(1, RoundingMode.HALF_UP));
    Assert.assertEquals(BigDecimal.valueOf(1490.0),
        playerTwo.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
        .setScale(1, RoundingMode.HALF_UP));

    // Match Two
    List<Player> matchTwoPlayers = Lists.newArrayList(playerOne, playerTwo);
    Match matchTwo = new Match("", matchTwoPlayers);
    matchTwo.reportScores(21, 17);

    result = calculator.calculate(matchTwo);
    Stream.concat(result).zipWithIndex()
    .forEach(t -> matchTwoPlayers.get(t._2).updateCurrentRatings(calculator.getRatingType(), t._1));


    Assert.assertEquals(BigDecimal.valueOf(1519.4),
        playerOne.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
        .setScale(1, RoundingMode.HALF_UP));
    Assert.assertEquals(BigDecimal.valueOf(1480.6),
        playerTwo.getCurrentRating(RatingType.ELO).map(Rating::getRating).get()
        .setScale(1, RoundingMode.HALF_UP));

    // Match Three
    List<Player> matchThreePlayers = Lists.newArrayList(playerOne, playerTwo);
    Match matchThree = new Match("", matchThreePlayers);
    matchThree.reportScores(14, 21);

    result = calculator.calculate(matchThree);
    Stream.concat(result).zipWithIndex()
    .forEach(t -> matchThreePlayers.get(t._2).updateCurrentRatings(calculator.getRatingType(), t._1));

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
    calculator.calculate(new Match("", Lists.newArrayList(playerOne)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateTooManyPlayers() throws Exception {
    calculator.calculate(new Match("", Lists.newArrayList(playerOne, playerTwo, playerThree)));
  }
}