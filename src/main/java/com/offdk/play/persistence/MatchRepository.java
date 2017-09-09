package com.offdk.play.persistence;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.offdk.play.model.game.Match;

import java.util.Optional;

public interface MatchRepository extends CrudRepository<Match, String> {
  @Query("{teamId: ?0, players: ?1, status: 'Active'}")
  Optional<Match> findActiveMatch(String teamId, String id);
}
