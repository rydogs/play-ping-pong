package com.offdk.play.rating;

import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import java.math.BigDecimal;
import java.util.Map;

public interface RatingCalculator {

  Map<Player, Map<RatingType, BigDecimal>> calculate(Match match);

}
