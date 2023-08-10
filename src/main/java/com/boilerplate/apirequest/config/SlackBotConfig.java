package com.boilerplate.apirequest.config;

import lombok.Getter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackBotConfig {
	@Value("${spring.slack.token.singing_secret}")
	private String singingSecret;

	@Value("${spring.slack.token.bot_token}")
	private String botToken;

	@Value("${spring.slack.token.app_token}")
	private String appToken;


}
