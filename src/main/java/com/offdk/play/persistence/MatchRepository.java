package com.offdk.play.persistence;

import org.springframework.data.repository.CrudRepository;

import com.offdk.play.model.game.Match;

public interface MatchRepository extends CrudRepository<Match, String> {

}
