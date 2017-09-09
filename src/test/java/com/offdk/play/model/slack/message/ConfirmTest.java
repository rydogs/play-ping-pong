package com.offdk.play.model.slack.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offdk.play.model.slack.message.Confirm;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfirmTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void testBuilder() throws Exception {
    Confirm confirm = ImmutableConfirm.builder()
        .text("TEXT")
        .title("TITLE")
        .okText("OK")
        .dismissText("DIMISS")
        .build();

    String json = mapper.writeValueAsString(confirm);

    Confirm newConfirm = mapper.readValue(json, Confirm.class);

    Assert.assertTrue(confirm.equals(newConfirm));
  }
}