package com.offdk.play.model.slack.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAction.class)
@JsonDeserialize(as = ImmutableAction.class)
@Value.Style(passAnnotations = {JsonNaming.class, JsonInclude.class}, forceJacksonPropertyNames = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_ABSENT)
public interface Action {

  String name();

  @Nullable // Needed for Callback
  String text();

  String type();

  @Nullable
  String value();

  @Nullable
  Confirm confirm();

  @Nullable
  Style style();

  @Nullable
  List<Option> options();

  @Nullable
  OptionGroup optionGroups();

  @Nullable
  String dataSource();

  @Nullable
  List<Option> selectedOptions();

  @Nullable
  Integer minQueryLength();

  static Action createButton(String group, String text) {
    return createButton(group, text, Style.DEFAULT);
  }

  static Action createButton(String group, String text, Style style) {
    Preconditions.checkArgument(text.length() < 30, "Text too long for button");

    return ImmutableAction.builder()
        .name(group)
        .text(text)
        .type(ActionType.BUTTON.toString())
        .style(style)
        .value(CaseFormat.UPPER_UNDERSCORE
            .to(CaseFormat.LOWER_CAMEL, text.toUpperCase().replace(" ", "_")))
        .build();
  }

  default Action addConfirmation() {
    Confirm confirm = ImmutableConfirm.builder()
        .text("Are you sure?")
        .okText("Yes")
        .dismissText("No")
        .build();

    return ImmutableAction.builder()
        .name(this.name())
        .text(this.text())
        .type(this.type())
        .value(this.value())
        .style(this.style())
        .confirm(confirm)
        .build();
  }
}
