package com.offdk.play.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharSource;
import com.offdk.play.model.SlackCommand;

public class SlackCommandParamHandlerTest {
  private static Joiner EQL_JOINER = Joiner.on("=");

  private SlackCommandParamHandler handler;

  @Before
  public void before() {
    handler = new SlackCommandParamHandler();
  }

  @Test
  public void handlTest() throws Exception {
    Map<String, String> requestMap = ImmutableMap.<String, String>builder()
        .put("token", "gIkuvaNzQIHg97ATvDxqgjtO")
        .put("team_id", "T0001")
        .put("team_domain", "example")
        .put("enterprise_id", "E0001")
        .put("enterprise_name", "Globular Construct Inc")
        .put("channel_id", "C2147483705")
        .put("channel_name", "test")
        .put("user_id", "U2147483697")
        .put("user_name", "Steve Bob")
        .put("command", "/weather")
        .put("text", RandomStringUtils.randomAlphabetic(20))
        .put("response_url", RandomStringUtils.randomAlphanumeric(20))
        .build();
    String testString = URLEncoder.encode(requestMap.entrySet().stream()
        .map(e -> EQL_JOINER.join(e.getKey(), e.getValue()))
        .collect(Collectors.joining("&")), Charsets.UTF_8.toString());

    NativeWebRequest request = mock(NativeWebRequest.class);
    HttpServletRequest httpRequest = mock(HttpServletRequest.class);

    BufferedReader reader = CharSource.wrap(testString).openBufferedStream();
    when(request.getNativeRequest(HttpServletRequest.class)).thenReturn(httpRequest);
    when(httpRequest.getReader()).thenReturn(reader);
    SlackCommand actual = (SlackCommand) handler.resolveArgument(mock(MethodParameter.class),
        mock(ModelAndViewContainer.class), request,
        mock(WebDataBinderFactory.class));

    assertEquals(requestMap.get("channel_name"), actual.getChannelName());
    assertEquals(requestMap.get("user_name"), actual.getCommandUser().getUserName());
  }
}
