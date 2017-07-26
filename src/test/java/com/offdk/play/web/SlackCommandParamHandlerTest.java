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
        .put("token", "KqQi709jvAqsFI2u5QynCu2G")
		.put("team_id", "T6ATNPZ88")
		.put("team_domain", "playpingpong")
		.put("channel_id", "C6CE9FBV4")
		.put("channel_name", "general")
		.put("user_id", "U6BMC8KT7")
		.put("user_name", "rydogs")
		.put("command", "/play")
        .put("text", RandomStringUtils.randomAlphabetic(20))
        .put("response_url", RandomStringUtils.randomAlphanumeric(20))
        .build();
     //String testString = URLEncoder.encode(requestMap.entrySet().stream()
     //   .map(e -> EQL_JOINER.join(e.getKey(), e.getValue()))
     //   .collect(Collectors.joining("&")), Charsets.UTF_8.toString());
    String testString = "token=KqQi709jvAqsFI2u5QynCu2G&team_id=T6ATNPZ88&team_domain=playpingpong&channel_id=C6CE9FBV4&channel_name=general&user_id=U6BMC8KT7&user_name=rydogs&command=%2Fplay&text=%40furman&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT6ATNPZ88%2F215127455105%2F3EEsrVjZVltVZu1LcrssfDsA";
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
