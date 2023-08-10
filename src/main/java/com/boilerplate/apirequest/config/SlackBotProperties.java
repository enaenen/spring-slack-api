package com.boilerplate.apirequest.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackBotProperties {

	@Value("${spring.slack.token.singing_secret}")
	private String singingSecret;

	@Value("${spring.slack.token.bot_token}")
	private String botToken;

	@Value("${spring.slack.token.app_token}")
	private String appToken;

	@Value("${spring.slack.token.channel}")
	private String channelId;


}
