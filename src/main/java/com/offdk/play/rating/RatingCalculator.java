package com.offdk.play.rating;

import com.offdk.play.model.game.Match;
import com.offdk.play.model.game.Player;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RatingCalculator {

  RatingType getRatingType();

  List<Rating> calculate(Match match);

}
