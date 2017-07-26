package com.offdk.play.rating;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.CollectionUtils;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Glicko_rating_system">Glicko Rating System</a>
 */
public class Glicko2RatingCalculator implements RatingCalculator {

  private static final int DEFAULT_RATING = 1500;
  private static final int DEFAULT_RATING_DEVIATION = 350;
  private static final BigDecimal VOLATILITY = BigDecimal.valueOf(0.6);
  private static final BigDecimal CONSTANT = BigDecimal.valueOf(173.7178);

  @Override
  public RatingType getRatingType() {
    return RatingType.GLICKO_2;
  }

  @Override
  public List<Rating> calculate(Match match) {
    Preconditions.checkNotNull(match, "Match cannot be null");
    Preconditions
        .checkArgument(!CollectionUtils.isEmpty(match.getPlayers()), "A match must have players");
    Preconditions
        .checkArgument(match.getPlayers().size() == 2, "The ELO algorithm requires two players");

    Entry<Player, Integer> playerOne = Iterables.get(match.getPlayersWithScore(), 0);
    Entry<Player, Integer> playerTwo = Iterables.get(match.getPlayersWithScore(), 1);

    // TODO: Add Glicko-2 Rating Algorithm
    return null;
  }

}
