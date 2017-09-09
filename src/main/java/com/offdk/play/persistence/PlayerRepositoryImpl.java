package com.offdk.play.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoOperations;

import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.User;

public class PlayerRepositoryImpl implements PlayerRepositoryCustom {
  private final MongoOperations operations;

  PlayerRepositoryImpl(MongoOperations operations) {
    this.operations = operations;
  }

  @Override
  public Player findOrCreate(String teamId, User user) {
    return Optional.ofNullable(user)
        .map(u -> operations.findById(u.getId(), Player.class))
        .orElseGet(
            () -> {
              Player p = Player.newPlayer(teamId, user);
              operations.insert(p);
              return p;
            });
  }
}