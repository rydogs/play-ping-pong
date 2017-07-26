package com.offdk.play.model.game;

import com.google.common.collect.Lists;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Match {

  private List<Entry<Player, Integer>> playersWithScore = Lists.newArrayList();

  public Match(List<Player> players) {
    players.forEach(player -> playersWithScore.add(new SimpleEntry<>(player, 0)));
  }

  public List<Player> getPlayers() {
    return playersWithScore.stream().map(Entry::getKey).collect(Collectors.toList());
  }

  public List<Entry<Player, Integer>> getPlayersWithScore() {
    return playersWithScore;
  }

  public void addScore(Player player, int score) {
    playersWithScore.stream().filter(p -> p.getKey().equals(player)).forEach(p -> p.setValue(score));
  }
}
