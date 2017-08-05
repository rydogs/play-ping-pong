package com.offdk.play.service;

import com.offdk.play.model.slack.request.Message;
import com.offdk.play.model.slack.response.CallbackRequest;

public interface CallbackProcessor {
  CallbackName name();

  Message process(CallbackRequest request);
}
