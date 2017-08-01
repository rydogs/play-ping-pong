package com.offdk.play.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.command.SlackCommand;
import com.offdk.play.model.slack.message.Action;
import com.offdk.play.model.slack.message.Action.Style;
import com.offdk.play.model.slack.message.Attachment;
import com.offdk.play.model.slack.message.Message;
import com.offdk.play.persistence.PlayerRepository;

@Service
public class SlackChatService {
  private final Logger LOGGER = LoggerFactory.getLogger(SlackChatService.class);

  private final PlayerRepository playerRepo;

  public SlackChatService(PlayerRepository playerRepo) {
    this.playerRepo = playerRepo;
  }

  // TODO: Implement logic to respond to command
  public Message respond(SlackCommand command) {
    Player player = Optional.ofNullable(command.commandUser())
        .flatMap(user -> playerRepo.findOneByUserId(user.getId()))
        .orElseGet(
            () -> playerRepo.save(Player.newPlayer(command.team().getId(), command.commandUser())));

    Message msg = Message.createInChannelMessage()
        .addText("Echo: " + command.text() + " from " + player)
        .addAttachment(Attachment.createAttachment("echoId", "This is a test", "Can you see me?")
            .addActions(Action.createButton("test", "Button One", Style.Primary),
                Action.createButton("test", "Button Two", Style.Danger).addConfirmation()));

    LOGGER.info(msg.toString());

    return msg;
  }
}
