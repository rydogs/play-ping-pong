package com.offdk.play.model.game;

import io.vavr.collection.Stream;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.offdk.play.model.slack.User;

public class Match {
  static final Joiner SPACE_JOINER = Joiner.on(" ");
  static final String CHALLENGE_FMT = "%s challenged %s for a game.";
  static final String CHALLENGE_ACCEPT_FMT = "%s accepted %s's challenge";
  static final String CHALLENGE_REFUSE_FMT = "%s refused %s's challenge";

  @Id
  private String id;
  private final String slackTeamId;
  private final List<MatchPlayer> players;
  private MatchStatus status;

  // Needed to allow spring-data to serialize data to object
  @PersistenceConstructor
  Match(String id, String slackTeamId, List<MatchPlayer> players, MatchStatus status) {
    this.id = id;
    this.slackTeamId = slackTeamId;
    this.players = players;
    this.status = status;
  }

  public Match(String slackTeamId, List<Player> playersList) {
    Preconditions.checkArgument(!playersList.isEmpty() && playersList.size() % 2 == 0,
        "A game must have even number of players");
    this.slackTeamId = slackTeamId;
    // divide the list of player in half, where first half is the challenger and second half is challenged
    this.players = Stream.concat(playersList).zipWithIndex()
        .partition(t -> t._2() < playersList.size() / 2)
        .map1(p1 -> p1.map(p -> MatchPlayer.newChallenger(p._1())))
        .map2(p2 -> p2.map(p -> MatchPlayer.newChallenged(p._1())))
        .apply((p1, p2) -> p1.appendAll(p2)).toJavaList();
    this.status = MatchStatus.CHALLENGED;
  }

  public String getId() {
    return this.id;
  }

  public MatchStatus getStatus() {
    return status;
  }

  public String getSlackTeamId() {
    return slackTeamId;
  }

  public List<MatchPlayer> getPlayers() {
    return players;
  }

  public void accept() {
    this.status = MatchStatus.ACCEPTED;
  }

  public void refuse() {
    this.status = MatchStatus.REFUSED;
  }

  public void reportScores(Integer... scores) {
    reportScores(Lists.newArrayList(scores));
  }

  public void reportScores(List<Integer> scores) {
    Preconditions.checkArgument(players.size() == scores.size(),
        "Reported score must have the same number of element as players");
    Stream.concat(scores).zipWithIndex().forEach(t -> players.get(t._2()).setScore(t._1()));
    this.status = MatchStatus.COMPLETED;
  }

  public List<MatchPlayer> getChallengers() {
    return Stream.concat(players).filter(p -> MatchPlayerType.CHALLENGER.equals(p.getType())).toJavaList();
  }

  public List<User> getChallengerUsers() {
    return toSlackUser(getChallengers());
  }

  public List<MatchPlayer> getChallenged() {
    return Stream.concat(players).filter(p -> MatchPlayerType.CHALLENGED.equals(p.getType())).toJavaList();
  }

  public List<User> getChallengedUsers() {
    return toSlackUser(getChallenged());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(Match.class).add("id", id).add("status", status)
        .add("players", players).toString();
  }

  public String challengeText() {
    return String.format(CHALLENGE_FMT, SPACE_JOINER.join(getChallengerUsers()),
        SPACE_JOINER.join(getChallengedUsers()));
  }

  public String acceptMatchText() {
    return String.format(CHALLENGE_ACCEPT_FMT, SPACE_JOINER.join(getChallengedUsers()),
        SPACE_JOINER.join(getChallengerUsers()));
  }

  public String refuseMatchText() {
    return String.format(CHALLENGE_REFUSE_FMT, SPACE_JOINER.join(getChallengedUsers()),
        SPACE_JOINER.join(getChallengerUsers()));
  }

  @VisibleForTesting
  static List<User> toSlackUser(List<MatchPlayer> players) {
    return players.stream().map(p -> p.getUser()).collect(Collectors.toList());
  }
}
