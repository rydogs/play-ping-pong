package com.offdk.play.persistence;

import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;

public interface PlayerRepositoryCustom {
  Player findOrCreate(String teamId, User user);
}
