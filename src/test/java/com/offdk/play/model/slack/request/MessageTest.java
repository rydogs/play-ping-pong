package com.offdk.play.model.slack.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void createInChannelMessage() throws Exception {
    Message message = Message.createInChannelMessage().build();

    String json = mapper.writeValueAsString(message);

    Message newMessage = mapper.readValue(json, Message.class);

    Assert.assertTrue(message.equals(newMessage));
    Assert.assertEquals(ResponseType.IN_CHANNEL, newMessage.responseType());
  }

  @Test
  public void createEphemeralMessage() throws Exception {
    Message message = Message.createEphemeralMessage().build();

    String json = mapper.writeValueAsString(message);

    Message newMessage = mapper.readValue(json, Message.class);

    Assert.assertTrue(message.equals(newMessage));
    Assert.assertEquals(ResponseType.EPHEMERAL, newMessage.responseType());
  }

  @Test
  public void addText() throws Exception {
    Message message = Message.createInChannelMessage().text("ADDING TEXT").build();

    String json = mapper.writeValueAsString(message);

    Message newMessage = mapper.readValue(json, Message.class);

    Assert.assertTrue(message.equals(newMessage));
    Assert.assertEquals("ADDING TEXT", newMessage.text());
  }

  @Test
  public void addAttachment() throws Exception {
    Attachment attachment = Attachment.createAttachment("ID", "TITLE", "DEFAULT");

    Message message = Message.createInChannelMessage().addAttachments(attachment).build();

    String json = mapper.writeValueAsString(message);

    Message newMessage = mapper.readValue(json, Message.class);

    Assert.assertTrue(message.equals(newMessage));
    Assert.assertEquals(1, newMessage.attachments().size());
  }

  @Test
  public void addAttachments() throws Exception {
    List<Attachment> attachments = Lists.newArrayList();
    for (int idx = 0; idx < 2; idx++) {
      attachments.add(Attachment.createAttachment("ID_" + idx, "TITLE_" + idx, "DEFAULT_" + idx));
    }

    Message message = Message.createInChannelMessage().addAllAttachments(attachments).build();

    String json = mapper.writeValueAsString(message);

    Message newMessage = mapper.readValue(json, Message.class);

    Assert.assertTrue(message.equals(newMessage));
    Assert.assertEquals(2, newMessage.attachments().size());
  }

  @Test(expected = IllegalStateException.class)
  public void addAttachmentsTooMany() throws Exception {
    List<Attachment> attachments = Lists.newArrayList();
    for (int idx = 0; idx < 30; idx++) {
      attachments.add(Attachment.createAttachment("ID_" + idx, "TITLE_" + idx, "DEFAULT_" + idx));
    }

    Message.createInChannelMessage().addAllAttachments(attachments).build();
  }
}