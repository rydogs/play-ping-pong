package com.offdk.play.rating;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.CollectionUtils;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Elo_rating_system">ELO Rating System</a>
 */
public class EloRatingCalculator implements RatingCalculator {

  private static final int DEFAULT_RATING = 1500;
  private static final int K_FACTOR = 20;

  @Override
  public Map<Player, Map<RatingType, Map<RatingKey, BigDecimal>>> calculate(Match match) {
    Preconditions.checkNotNull(match, "Match cannot be null");
    Preconditions
        .checkArgument(!CollectionUtils.isEmpty(match.getPlayers()), "A match must have players");
    Preconditions
        .checkArgument(match.getPlayers().size() == 2, "The ELO algorithm requires two players");

    Entry<Player, Integer> playerOne = Iterables.get(match.getPlayersWithScore(), 0);
    Entry<Player, Integer> playerTwo = Iterables.get(match.getPlayersWithScore(), 1);

    BigDecimal playerOneCurrentRating = getCurrentRating(playerOne.getKey());
    BigDecimal playerTwoCurrentRating = getCurrentRating(playerTwo.getKey());

    BigDecimal playerOneScore = calculateScore(playerOneCurrentRating);
    BigDecimal playerTwoScore = calculateScore(playerTwoCurrentRating);

    BigDecimal playerOneExp = calculateExpectedWinPercentage(playerOneScore, playerTwoScore);
    BigDecimal playerTwoExp = calculateExpectedWinPercentage(playerTwoScore, playerOneScore);

    BigDecimal playerOneNewRating =
        generateNewRating(playerOneCurrentRating, playerOneExp,
            playerOne.getValue() > playerTwo.getValue());
    BigDecimal playerTwoNewRating =
        generateNewRating(playerTwoCurrentRating, playerTwoExp,
            playerTwo.getValue() > playerOne.getValue());

    return ImmutableMap.of(
        playerOne.getKey(),
        ImmutableMap.of(RatingType.ELO, ImmutableMap.of(RatingKey.RATING, playerOneNewRating)),
        playerTwo.getKey(),
        ImmutableMap.of(RatingType.ELO, ImmutableMap.of(RatingKey.RATING, playerTwoNewRating)));
  }

  private BigDecimal getCurrentRating(Player player) {
    return player.getCurrentRating(RatingType.ELO).map(rating -> rating.get(RatingKey.RATING))
        .orElse(BigDecimal.valueOf(DEFAULT_RATING));
  }

  private BigDecimal calculateScore(BigDecimal rating) {
    return BigDecimal
        .valueOf(Math.pow(10.0, rating.divide(BigDecimal.valueOf(400L)).doubleValue()));
  }

  private BigDecimal calculateExpectedWinPercentage(BigDecimal rating, BigDecimal opponentRating) {
    return rating.divide(rating.add(opponentRating), 10, RoundingMode.HALF_EVEN);
  }

  private BigDecimal generateNewRating(BigDecimal rating, BigDecimal expectedWinPercentage,
      boolean isWinner) {
    return rating.add(BigDecimal.valueOf(K_FACTOR).multiply(
        BigDecimal.valueOf(Boolean.compare(isWinner, false)).subtract(expectedWinPercentage)));
  }
}
