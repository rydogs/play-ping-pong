package com.offdk.play.model.slack.request.parser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.offdk.play.model.slack.User;
import com.offdk.play.model.slack.request.parser.UserParser;

public class UserParserTest {
  private UserParser actualResult;
  @Before
  public void setUp(){
    actualResult = new UserParser();
  }

  @Test
  public void applyMultipleTest() throws Exception{
    List<User> expectedResult = new ArrayList<User>();
    expectedResult.add(new User("U012ABCDEF", "ernie"));
    expectedResult.add(new User("Z012ABCDEZ", "rydogs"));
    Assert.assertEquals(expectedResult, 
        actualResult.apply("text=<@U012ABCDEF|ernie> don't wake me up at night anymore in <#C012ABCDE|here>,"
            + "text=<@Z012ABCDEZ|rydogs> don't wake me up at night anymore in <#C012ABCDE|here>"));
  }

  public void applyOneTest() throws Exception{
    List<User> expectedResult = new ArrayList<User>();
    expectedResult.add(new User("U012ABCDEF", "ernie"));
    Assert.assertEquals(expectedResult, 
        actualResult.apply("text=<@U012ABCDEF|ernie> don't wake me up at night anymore in <#C012ABCDE|here>"));
  }

  public void applyEmptyTest() throws Exception{
    List<User> expectedResult3 = new ArrayList<User>();
    Assert.assertEquals(expectedResult3, 
        actualResult.apply(""));
  }
  
}
