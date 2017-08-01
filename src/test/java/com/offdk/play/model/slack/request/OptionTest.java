package com.offdk.play.model.slack.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void testBuilder() throws Exception {
    Option option = ImmutableOption.builder()
        .text("TEXT")
        .value("VALUE")
        .description("DESCRIPTION")
        .build();

    String json = mapper.writeValueAsString(option);

    Option newOption = mapper.readValue(json, Option.class);

    Assert.assertTrue(option.equals(newOption));
  }
}