package com.offdk.play.web;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.offdk.play.model.slack.User;

public class UserParser implements Function<String, List<User>> {

	@Override
	public List<User> apply(String str) {
		List<User> output= new ArrayList<User>();
		Pattern pattern = Pattern.compile("<(.*?)>");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			String stringUser = matcher.group(1);
			if (stringUser.startsWith("@")){
				String[] arrayUser = stringUser.substring(1).split("\\|");
				User newUser = new User(arrayUser[0], arrayUser[1]);
				output.add(newUser);
			}
		}
		return output;
	}
}