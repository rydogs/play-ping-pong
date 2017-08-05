package com.offdk.play.rating;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.MatchPlayer;
import com.offdk.play.model.game.MatchStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Elo_rating_system">ELO Rating System</a>
 */
public class EloRatingCalculator implements RatingCalculator {

  private static final BigDecimal DEFAULT_RATING = BigDecimal.valueOf(1500);
  private static final int K_FACTOR = 20;

  @Override
  public RatingType getRatingType() {
    return RatingType.ELO;
  }

  @Override
  public List<Rating> calculate(Match match) {
    Preconditions.checkNotNull(match, "Match cannot be null");
    Preconditions
    .checkArgument(!CollectionUtils.isEmpty(match.getPlayers()), "A match must have players");
    Preconditions
    .checkArgument(match.getPlayers().size() == 2, "The ELO algorithm requires two players");
    Preconditions.checkState(MatchStatus.COMPLETED.equals(match.getStatus()),
        "A match must be completed to calculate new ratings");

    MatchPlayer playerOne = match.getChallengers().get(0);
    MatchPlayer playerTwo = match.getChallenged().get(0);

    BigDecimal playerOneCurrentRating = playerOne.getRating().orElse(DEFAULT_RATING);
    BigDecimal playerTwoCurrentRating = playerTwo.getRating().orElse(DEFAULT_RATING);

    BigDecimal playerOneScore = calculateScore(playerOneCurrentRating);
    BigDecimal playerTwoScore = calculateScore(playerTwoCurrentRating);

    BigDecimal playerOneExp = calculateExpectedWinPercentage(playerOneScore, playerTwoScore);
    BigDecimal playerTwoExp = calculateExpectedWinPercentage(playerTwoScore, playerOneScore);

    boolean playerOneWon = playerOne.getScore().get() > playerTwo.getScore().get();
    EloRating playerOneNewRating = generateNewRating(playerOneCurrentRating, playerOneExp, playerOneWon);
    EloRating playerTwoNewRating = generateNewRating(playerTwoCurrentRating, playerTwoExp, !playerOneWon);

    return ImmutableList.of(playerOneNewRating, playerTwoNewRating);
  }

  private BigDecimal calculateScore(BigDecimal rating) {
    return BigDecimal
        .valueOf(Math.pow(10.0, rating.divide(BigDecimal.valueOf(400L)).doubleValue()));
  }

  private BigDecimal calculateExpectedWinPercentage(BigDecimal rating, BigDecimal opponentRating) {
    return rating.divide(rating.add(opponentRating), 10, RoundingMode.HALF_EVEN);
  }

  private EloRating generateNewRating(BigDecimal rating, BigDecimal expectedWinPercentage,
      boolean isWinner) {
    return new EloRating(rating.add(BigDecimal.valueOf(K_FACTOR).multiply(
        BigDecimal.valueOf(Boolean.compare(isWinner, false)).subtract(expectedWinPercentage))));
  }
}
