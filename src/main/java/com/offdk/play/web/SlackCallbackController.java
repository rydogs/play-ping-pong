package com.offdk.play.web;

import java.io.IOException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.offdk.play.model.slack.command.CallbackRequest;
import com.offdk.play.model.slack.message.ImmutableMessage;
import com.offdk.play.model.slack.message.Message;

@Controller
public class SlackCallbackController {
  private final Logger LOGGER = LoggerFactory.getLogger(SlackCallbackController.class);

  private final ObjectMapper objectMapper;

  public SlackCallbackController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @PostMapping("/callback")
  @ResponseBody
  public Message command(@RequestParam("payload") String requestStr) throws JsonParseException, JsonMappingException, IOException {
    //TODO: handle callback properly
    String decodedRequest = URLDecoder.decode(requestStr, Charsets.UTF_8.toString());
    CallbackRequest request =  objectMapper.readValue(decodedRequest, CallbackRequest.class);
    LOGGER.info("Request: {}", request);
    return ImmutableMessage.builder().text("Respoding to request: " + request)
        .deleteOriginal(false).build();
  }
}
