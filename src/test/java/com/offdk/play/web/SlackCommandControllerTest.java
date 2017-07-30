package com.offdk.play.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SlackCommandControllerTest {
  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandControllerTest.class);

  @Autowired
  private TestRestTemplate restTemplate;
  @Test
  public void controllerTest() {
    Map<String, String> requestMap = ImmutableMap.<String, String> builder().put("token", "KqQi709jvAqsFI2u5QynCu2G")
        .put("team_id", "T6ATNPZ88")
        .put("team_domain", "playpingpong")
        .put("channel_id", "C6CE9FBV4")
        .put("channel_name", "general")
        .put("user_id", "U6BMC8KT7")
        .put("user_name", "George Wang")
        .put("command", "/play")
        .put("text", RandomStringUtils.randomAlphabetic(20))
        .put("response_url", RandomStringUtils.randomAlphanumeric(20)).build();
    ResponseEntity<String> response = restTemplate.postForEntity("/command", requestMap, String.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
    LOGGER.info("Body: {}", response.getBody());
  }
}
