package com.offdk.play.web;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.offdk.play.model.slack.SlackCommand;
import com.offdk.play.model.slack.User;

import io.vavr.control.Try;

public class SlackCommandParamHandler implements HandlerMethodArgumentResolver {

  public Object resolveArgument(MethodParameter param, ModelAndViewContainer container, NativeWebRequest request,
      WebDataBinderFactory dataBinderFactory) throws Exception {
    HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
    String requestBody = Try.of(() -> httpRequest.getReader())
        // read post request body as a string
        .mapTry(reader -> CharStreams.toString(reader))
        // Url decode the string
        .mapTry(str -> URLDecoder.decode(str, Charsets.UTF_8.toString())).get();
    Map<String, String> parserMap = Maps.newHashMap();
    for (String element : requestBody.split("&")) {
      String[] keyValue = element.split("=");
      if (keyValue.length == 2) {
        parserMap.put(keyValue[0].trim(), keyValue[1].trim());
      }
    }

    SlackCommand mySlackCommand = new SlackCommand();

    mySlackCommand.setToken(parserMap.get("token"));
    mySlackCommand.setTeamId(parserMap.get("team_id"));
    mySlackCommand.setTeamDomain(parserMap.get("team_domain"));
    mySlackCommand.setChannelId(parserMap.get("channel_id"));
    mySlackCommand.setChannelName(parserMap.get("channel_name"));
    User commandUser = new User(parserMap.get("user_id"), parserMap.get("user_name"));
    mySlackCommand.setCommandUser(commandUser);
    mySlackCommand.setCommand(parserMap.get("command"));
    mySlackCommand.setText(parserMap.get("text"));
    mySlackCommand.setResponseUrl(parserMap.get("response_url"));

    return mySlackCommand;
  }

  public boolean supportsParameter(MethodParameter param) {
    return param.hasParameterAnnotation(SlackCommandParam.class);
  }
}