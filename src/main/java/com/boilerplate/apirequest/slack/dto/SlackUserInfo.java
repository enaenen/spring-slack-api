package com.boilerplate.apirequest.slack.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class SlackUserInfo {
	String id;
	String name;
	@JsonAlias("real_name")
	String realName;
	@JsonAlias("team_id")
	String teamId;
	Boolean deleted;
}
