package com.offdk.play.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.offdk.play.model.slack.SlackCommand;

public class SlackCommandParamHandler implements HandlerMethodArgumentResolver {

  public Object resolveArgument(MethodParameter param, ModelAndViewContainer container, NativeWebRequest request,
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
    return new SlackCommand();
  }

  public boolean supportsParameter(MethodParameter param) {
    return param.hasParameterAnnotation(SlackCommandParam.class);
  }
}
