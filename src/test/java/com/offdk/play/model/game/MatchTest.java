package com.offdk.play.model.game;

import com.google.common.collect.Lists;
import com.offdk.play.model.slack.User;
import java.util.List;
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
  public void getStatusDefaultTest() throws Exception {
    Match match = new Match("", Lists.newArrayList(playerOne, playerTwo));

    Assert.assertTrue(MatchStatus.CHALLENGED.equals(match.getStatus()));
  }

  @Test
  public void getPlayersTest() throws Exception {
    Match match = new Match("", Lists.newArrayList(playerOne, playerTwo));

    List<MatchPlayer> players = match.getPlayers();

    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne.getId(), players.get(0).getUser().getId());
    Assert.assertEquals(playerTwo.getId(), players.get(1).getUser().getId());
  }

  @Test
  public void getPlayersWithScoreInChallengedStateTest() throws Exception {
    Match match = new Match("", Lists.newArrayList(playerOne, playerTwo));

    List<MatchPlayer> players = match.getPlayers();

    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne.getId(), players.get(0).getUser().getId());
    Assert.assertFalse(players.get(0).getScore().isPresent());

    Assert.assertEquals(playerTwo.getId(), players.get(1).getUser().getId());
    Assert.assertFalse(players.get(1).getScore().isPresent());
  }

  @Test(expected = IllegalArgumentException.class)
  public void reportScoreWrongNumberTest() throws Exception {
    Match match = new Match("", Lists.newArrayList(playerOne, playerTwo));
    match.reportScores(Lists.newArrayList(21, 18, 8));
  }

  @Test
  public void getPlayersWithScoreInCompletedStateTest() throws Exception {
    Match match = new Match("", Lists.newArrayList(playerOne, playerTwo));
    match.reportScores(Lists.newArrayList(21, 18));

    List<MatchPlayer> players = match.getPlayers();

    Assert.assertEquals(MatchStatus.COMPLETED, match.getStatus());
    Assert.assertEquals(2, players.size());

    Assert.assertEquals(playerOne.getId(), players.get(0).getUser().getId());
    Assert.assertEquals(21, players.get(0).getScore().get().intValue());

    Assert.assertEquals(playerTwo.getId(), players.get(1).getUser().getId());
    Assert.assertEquals(18, players.get(1).getScore().get().intValue());
  }
}