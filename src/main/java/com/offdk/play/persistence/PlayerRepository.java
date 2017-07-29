package com.offdk.play.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.offdk.play.model.game.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
  Optional<Player> findOneByUserUserId(String userId);
}
