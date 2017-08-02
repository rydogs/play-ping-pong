package com.offdk.play.service;

import com.offdk.play.model.game.Player;
import com.offdk.play.model.slack.request.Action;
import com.offdk.play.model.slack.request.Attachment;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.request.Style;
import com.offdk.play.model.slack.response.SlackCommand;
import com.offdk.play.persistence.PlayerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
            .addActions(Action.createButton("test", "Button One", Style.PRIMARY),
                Action.createButton("test", "Button Two", Style.DANGER).addConfirmation()));

    LOGGER.info(msg.toString());

    return msg;
  }
}
