package com.boilerplate.apirequest.slack.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SlackResponse {
	private final String ok;
	@JsonAlias("user")
	private final SlackUserInfo slackUserInfo;
}
