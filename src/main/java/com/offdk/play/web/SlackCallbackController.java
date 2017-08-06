package com.offdk.play.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;
import com.offdk.play.service.GameService;

import java.io.IOException;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SlackCallbackController {

  private final Logger LOGGER = LoggerFactory.getLogger(SlackCallbackController.class);

  private final GameService gameService;
  private final ObjectMapper objectMapper;

  public SlackCallbackController(GameService gameService, ObjectMapper objectMapper) {
    this.gameService = gameService;
    this.objectMapper = objectMapper;
  }

  @PostMapping("/callback")
  @ResponseBody
  public Message command(@RequestParam("payload") String requestStr) throws IOException {
    //TODO: handle callback properly
    String decodedRequest = URLDecoder.decode(requestStr, Charsets.UTF_8.toString());
    CallbackRequest request = objectMapper.readValue(decodedRequest, CallbackRequest.class);
    LOGGER.info("Request: {}", request);
    return gameService.handleCallback(request);
  }
}
