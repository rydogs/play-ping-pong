package com.offdk.play.model.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Match {

  private MatchStatus status;
  private List<Entry<Player, Integer>> playersWithScore;

  public Match(List<Player> players) {
    this.status = MatchStatus.CHALLENGED;
    this.playersWithScore = Lists.newArrayList();

    players.forEach(player -> playersWithScore.add(new SimpleEntry<>(player, 0)));
  }

  public MatchStatus getStatus() {
    return status;
  }

  public void setStatus(MatchStatus status) {
    this.status = status;
  }

  public List<Player> getPlayers() {
    return playersWithScore.stream().map(Entry::getKey).collect(Collectors.toList());
  }

  public List<Entry<Player, Integer>> getPlayersWithScore() {
    return playersWithScore;
  }

  public void addScore(Player player, int score) {
    Preconditions.checkState(MatchStatus.COMPLETED.equals(status),
        "A match must be completed to add a score");

    playersWithScore.stream().filter(p -> p.getKey().equals(player))
        .forEach(p -> p.setValue(score));
  }
}
