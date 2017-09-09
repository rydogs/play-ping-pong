package com.offdk.play.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharSource;
import com.offdk.play.model.slack.request.SlackCommand;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.UriUtils;

public class SlackCommandParamHandlerTest {

  private final Logger LOGGER = LoggerFactory.getLogger(SlackCommandController.class);
  private static Joiner EQL_JOINER = Joiner.on("=");

  private SlackCommandParamHandler handler;

  @Before
  public void before() {
    handler = new SlackCommandParamHandler();
  }

  @Test
  public void handleTest() throws Exception {
    Map<String, String> requestMap = ImmutableMap.<String, String>builder()
        .put("token", "KqQi709jvAqsFI2u5QynCu2G")
        .put("team_id", "T6ATNPZ88")
        .put("team_domain", "playpingpong")
        .put("channel_id", "C6CE9FBV4")
        .put("channel_name", "general")
        .put("user_id", "U6BMC8KT7")
        .put("user_name", "George Wang")
        .put("command", "/play")
        .put("text", RandomStringUtils.randomAlphabetic(20))
        .put("response_url", RandomStringUtils.randomAlphanumeric(20)).build();
    String testString = requestMap.entrySet().stream()
        .map(e -> EQL_JOINER.join(e.getKey(), encode(e.getValue())))
        .collect(Collectors.joining("&"));
    LOGGER.info("Test command is {}", testString);

    NativeWebRequest request = mock(NativeWebRequest.class);
    HttpServletRequest httpRequest = mock(HttpServletRequest.class);

    BufferedReader reader = CharSource.wrap(testString).openBufferedStream();
    when(request.getNativeRequest(HttpServletRequest.class)).thenReturn(httpRequest);
    when(httpRequest.getReader()).thenReturn(reader);
    SlackCommand actual = (SlackCommand) handler.resolveArgument(mock(MethodParameter.class),
        mock(ModelAndViewContainer.class), request, mock(WebDataBinderFactory.class));

    assertEquals(requestMap.get("channel_name"), actual.channel().getName());
    assertEquals(requestMap.get("user_name"), actual.commandUser().getName());
  }

  static String encode(String value) {
    return Try.of(() -> value).mapTry(v -> UriUtils.encodePath(v, Charsets.UTF_8.toString())).get();
  }
}
