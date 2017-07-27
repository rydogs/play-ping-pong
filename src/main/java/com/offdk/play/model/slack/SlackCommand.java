package com.offdk.play.model.slack;

public class SlackCommand {
  private String token;
  private String teamId;
  private String teamDomain;
  private String channelId;
  private User commandUser;
  private String text;
  private String responseUrl;
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public String getTeamId() {
    return teamId;
  }
  public void setTeamId(String teamId) {
    this.teamId = teamId;
  }
  public String getTeamDomain() {
    return teamDomain;
  }
  public void setTeamDomain(String teamDomain) {
    this.teamDomain = teamDomain;
  }
  public String getChannelId() {
    return channelId;
  }
  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
  public User getCommandUser() {
    return commandUser;
  }
  public void setCommandUser(User commandUser) {
    this.commandUser = commandUser;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getResponseUrl() {
    return responseUrl;
  }
  public void setResponseUrl(String responseUrl) {
    this.responseUrl = responseUrl;
  }
}
