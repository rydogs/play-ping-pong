package com.offdk.play.rating;

import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Glicko_rating_system">Glicko Rating System</a>
 */
public class Glicko2RatingCalculator implements RatingCalculator {

  @Override
  public Map<Player, Map<RatingType, BigDecimal>> calculate(Match match) {
    // TODO: Add Glicko-2 Rating Algorithm
    return null;
  }
}
