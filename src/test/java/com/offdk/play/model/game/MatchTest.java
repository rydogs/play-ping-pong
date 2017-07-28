package com.offdk.play.model.game;

import com.google.common.collect.Lists;
import com.offdk.play.model.slack.User;
import java.util.List;
import java.util.Map.Entry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatchTest {

  private Player playerOne;
  private Player playerTwo;

  @Before
  public void setUp() throws Exception {
    playerOne = new Player("1", new User("0", "Rick Sanchez"));
    playerTwo = new Player("2", new User("1", "Morty Smith"));
  }

  @Test
  public void getStatus() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));

    Assert.assertTrue(MatchStatus.CHALLENGED.equals(match.getStatus()));
  }

  @Test
  public void setStatus() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));
    match.setStatus(MatchStatus.ACCEPTED);

    Assert.assertTrue(MatchStatus.ACCEPTED.equals(match.getStatus()));
  }

  @Test
  public void getPlayers() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));

    List<Player> players = match.getPlayers();

    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne, players.get(0));
    Assert.assertEquals(playerTwo, players.get(1));
  }

  @Test
  public void getPlayersWithScoreInChallengedState() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));

    List<Entry<Player, Integer>> players = match.getPlayersWithScore();

    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne, players.get(0).getKey());
    Assert.assertEquals(0, players.get(0).getValue().intValue());

    Assert.assertEquals(playerTwo, players.get(1).getKey());
    Assert.assertEquals(0, players.get(1).getValue().intValue());
  }

  @Test(expected = IllegalStateException.class)
  public void addScoreInNonCompletedState() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));
    match.addScore(playerOne, 21);
    match.addScore(playerTwo, 18);
  }

  @Test
  public void getPlayersWithScoreInCompletedState() throws Exception {
    Match match = new Match(Lists.newArrayList(playerOne, playerTwo));
    match.setStatus(MatchStatus.COMPLETED);
    match.addScore(playerOne, 21);
    match.addScore(playerTwo, 18);

    List<Entry<Player, Integer>> players = match.getPlayersWithScore();

    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne, players.get(0).getKey());
    Assert.assertEquals(21, players.get(0).getValue().intValue());

    Assert.assertEquals(playerTwo, players.get(1).getKey());
    Assert.assertEquals(18, players.get(1).getValue().intValue());
  }
}