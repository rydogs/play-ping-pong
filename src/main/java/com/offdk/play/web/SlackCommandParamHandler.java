package com.offdk.play.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.offdk.play.model.SlackCommand;

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

        //List<String> list = request.getNativeRequest().split("{}")[1].split("&");
        String[] arr = request.getNativeRequest().split("&");
        HashMap<String, String> d = new HashMap<String, String>();  

        for (String a : arr){
          List<String> key_value = a.split("=");
          d.put(key_value[0], key_value[1]);
        }
        
        SlackCommand mySlackCommand = new SlackCommand();

        mySlackCommand.setToken(d.get("token"));
        mySlackCommand.setTeamId(d.get("team_id"));
        mySlackCommand.setTeamDomain(d.get("team_domain"));
        mySlackCommand.setChannelId(d.get("channel_id"));
        mySlackCommand.setChannelName(d.get("channel_name"));
        User commandUser = new User(d.get("user_id"), d.get("user_name"))
        mySlackCommand.setCommandUser(commandUser);
        mySlackCommand.setCommand(d.get("command"));
        mySlackCommand.setText(d.get("text"));
        mySlackCommand.setResponseUrl(d.get("response_url"));

        return mySlackCommand;
  }

  public boolean supportsParameter(MethodParameter param) {
    return param.hasParameterAnnotation(SlackCommandParam.class);
  }
}
