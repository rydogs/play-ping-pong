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
public class OptionGroupTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void testBuilder() throws Exception {
    Option optionOne = ImmutableOption.builder()
        .text("TEXT_1")
        .value("VALUE_1")
        .description("DESCRIPTION_1")
        .build();

    Option optionTwo = ImmutableOption.builder()
        .text("TEXT_2")
        .value("VALUE_2")
        .description("DESCRIPTION_2")
        .build();

    OptionGroup optionGroup = ImmutableOptionGroup.builder()
        .text("TEXT")
        .addOptions(optionOne, optionTwo)
        .build();

    String json = mapper.writeValueAsString(optionGroup);

    OptionGroup newOptionGroup = mapper.readValue(json, OptionGroup.class);

    Assert.assertTrue(optionGroup.equals(newOptionGroup));
  }
}