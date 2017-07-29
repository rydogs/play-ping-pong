package com.offdk.play.service;

import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.SlackCommand;
import com.offdk.play.persistence.PlayerRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SlackChatService {
  private final PlayerRepository playerRepo;

  public SlackChatService(PlayerRepository playerRepo) {
    this.playerRepo = playerRepo;
  }

  public SlackResponse respond(SlackCommand command) {
    //TODO implement actual logic to respond to command. For now, echo
    Player player = Optional.ofNullable(command.getCommandUser())
        .flatMap(u -> playerRepo.findOneByUserUserId(u.getUserId()))
        .orElseGet(() -> playerRepo.save(Player.newPlayer(command.getTeamId(), command.getCommandUser())));
    return SlackResponse.inChannel("Echo: " + command.getText() + " from " + player);
  }
}
