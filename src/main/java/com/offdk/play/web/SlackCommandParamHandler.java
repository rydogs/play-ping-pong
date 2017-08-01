package com.offdk.play.web;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.offdk.play.model.slack.Channel;
import com.offdk.play.model.slack.Team;
import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.response.ImmutableSlackCommand;
import io.vavr.control.Try;
import java.net.URLDecoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SlackCommandParamHandler implements HandlerMethodArgumentResolver {

  public Object resolveArgument(MethodParameter param, ModelAndViewContainer container,
      NativeWebRequest request,
      WebDataBinderFactory dataBinderFactory) throws Exception {

    HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
    String requestBody = Try.of(() -> httpRequest.getReader())
        //read post request body as a string
        .mapTry(reader -> CharStreams.toString(reader))
        //Url decode the string
        .mapTry(str -> URLDecoder.decode(str, Charsets.UTF_8.toString())).get();
    Map<String, String> parserMap = Maps.newHashMap();
    for (String element : requestBody.split("&")) {
      String[] keyValue = element.split("=");
      if (keyValue.length == 2) {
        parserMap.put(keyValue[0].trim(), keyValue[1].trim());
      }
    }

    User commandUser = new User(parserMap.get("user_id"), parserMap.get("user_name"));
    Team team = new Team(parserMap.get("team_id"), parserMap.get("team_domain"));
    Channel channel = new Channel(parserMap.get("channel_id"), parserMap.get("channel_name"));

    return ImmutableSlackCommand.builder()
        .token(parserMap.get("token"))
        .team(team)
        .channel(channel)
        .commandUser(commandUser)
        .command(parserMap.get("command"))
        .text(parserMap.get("text"))
        .responseUrl(parserMap.get("response_url"))
        .build();
  }

  public boolean supportsParameter(MethodParameter param) {
    return param.hasParameterAnnotation(SlackCommandParam.class);
  }
}