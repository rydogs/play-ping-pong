package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offdk.play.model.slack.message.Action;
import com.offdk.play.model.slack.message.Confirm;
import com.offdk.play.model.slack.message.Style;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void createButton() throws Exception {
    Action action = Action.createButton("GROUP", "TEXT");

    String json = mapper.writeValueAsString(action);

    Action newAction = mapper.readValue(json, Action.class);

    Assert.assertTrue(action.equals(newAction));
    Assert.assertEquals(Style.DEFAULT, newAction.style());
  }

  @Test
  public void createButtonWithStyle() throws Exception {
    Action action = Action.createButton("GROUP", "TEXT", Style.DANGER);

    String json = mapper.writeValueAsString(action);

    Action newAction = mapper.readValue(json, Action.class);

    Assert.assertTrue(action.equals(newAction));
    Assert.assertEquals(Style.DANGER, newAction.style());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createButtonTextTooLong() throws Exception {
    Action.createButton("GROUP", "TEXT IS WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAY TOO LONG");
  }

  @Test
  public void createButtonTextWithSpace() throws Exception {
    Action action = Action.createButton("GROUP", "TEXT HAS SPACES");

    String json = mapper.writeValueAsString(action);

    Action newAction = mapper.readValue(json, Action.class);

    Assert.assertTrue(action.equals(newAction));
    Assert.assertEquals("textHasSpaces", newAction.value());
  }

  @Test
  public void addConfirmation() throws Exception {
    Action action = Action.createButton("GROUP", "TEXT").addConfirmation();

    String json = mapper.writeValueAsString(action);

    Action newAction = mapper.readValue(json, Action.class);

    Assert.assertTrue(action.equals(newAction));

    Confirm confirm = newAction.confirm();
    Assert.assertEquals("Are you sure?", confirm.text());
    Assert.assertEquals("Yes", confirm.okText());
    Assert.assertEquals("No", confirm.dismissText());
  }
}