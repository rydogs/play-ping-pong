package com.offdk.play.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.net.UrlEscapers;
import com.offdk.play.model.SlackCommand;
import com.offdk.play.model.User;

import io.vavr.control.Try;

public class SlackCommandParamHandler implements HandlerMethodArgumentResolver {

	public Object resolveArgument(MethodParameter param, 
			ModelAndViewContainer container, 
			NativeWebRequest request,
			WebDataBinderFactory dataBinderFactory) throws Exception {
		/*
		 * TODO: Implement parser for a command request: https://api.slack.com/slash-commands
		 * Example:
		 *  token=gIkuvaNzQIHg97ATvDxqgjtO
        team_id=T0001
        team_domain=example
        enterprise_id=E0001
        enterprise_name=Globular%20Construct%20Inc
        channel_id=C2147483705
        channel_name=test
        user_id=U2147483697
        user_name=Steve
        command=/weather
        text=94070
        response_url=https://hooks.slack.com/commands/1234/5678
		 */

	  HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
	  String requestBody = Try.of(() -> httpRequest.getReader())
	      //read post request body as a string
	      .mapTry(reader -> CharStreams.toString(reader))
	      //Url escape the string
	      .map(str -> UrlEscapers.urlFragmentEscaper().escape(str)).get();
	  Map<String, String> parserMap = Maps.newHashMap();
		for (String element : requestBody.split("&")) {
			String[] key_value = element.split("=");
			parserMap.put(key_value[0], key_value[1]);
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
