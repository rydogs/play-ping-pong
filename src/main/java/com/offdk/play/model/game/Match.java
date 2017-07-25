package com.offdk.play.model.game;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Match {

  private Map<Player, Integer> players = Maps.newHashMap();

  public Match(List<Player> players) {
    players.forEach(player -> this.players.putIfAbsent(player, 0));
  }

  public Collection<Player> getPlayers() {
    return players.keySet();
  }

  public Collection<Entry<Player, Integer>> getPlayersWithScore() {
    return players.entrySet();
  }

  public void addScore(Player player, int score) {
    players.replace(player, score);
  }
}
