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
public class AttachmentTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void createAttachment() throws Exception {
    Attachment attachment = Attachment.createAttachment("ID", "TITLE", "DEFAULT");

    String json = mapper.writeValueAsString(attachment);

    Attachment newAttachment = mapper.readValue(json, Attachment.class);

    Assert.assertTrue(attachment.equals(newAttachment));
    Assert.assertTrue(newAttachment.actions().isEmpty());
    Assert.assertEquals(AttachmentType.DEFAULT, newAttachment.attachmentType());
  }

  @Test
  public void addAction() throws Exception {
    Action action = Action.createButton("GROUP", "TEXT");

    Attachment attachment = Attachment.createAttachment("ID", "TITLE", "DEFAULT").addAction(action);

    String json = mapper.writeValueAsString(attachment);

    Attachment newAttachment = mapper.readValue(json, Attachment.class);

    Assert.assertTrue(attachment.equals(newAttachment));
    Assert.assertEquals(1, newAttachment.actions().size());
  }

  @Test
  public void addActions() throws Exception {
    List<Action> actions = Lists.newArrayList();
    for (int idx = 0; idx < 2; idx++) {
      actions.add(Action.createButton("GROUP_" + idx, "TEXT_" + idx));
    }

    Attachment attachment = Attachment.createAttachment("ID", "TITLE", "DEFAULT")
        .addActions(actions.toArray(new Action[0]));

    String json = mapper.writeValueAsString(attachment);

    Attachment newAttachment = mapper.readValue(json, Attachment.class);

    Assert.assertTrue(attachment.equals(newAttachment));
    Assert.assertEquals(2, newAttachment.actions().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addActionsTooManyActions() throws Exception {
    List<Action> actions = Lists.newArrayList();
    for (int idx = 0; idx < 6; idx++) {
      actions.add(Action.createButton("GROUP_" + idx, "TEXT_" + idx));
    }

    Attachment.createAttachment("ID", "TITLE", "DEFAULT")
        .addActions(actions.toArray(new Action[0]));
  }
}